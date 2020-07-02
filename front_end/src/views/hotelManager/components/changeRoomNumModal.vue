<template>
    <div>
        <a-modal
                :visible="changeRoomNumModalVisible"
                title="更改房间数量"
                @cancel="handelCancel"
        >
            <template slot="footer">
                <a-button key="back" @click="handelCancel">
                    返回
                </a-button>
                <a-button key="submit" type="primary" @click="handleSubmit">
                    确定
                </a-button>
            </template>
            <a-form :form="form">
                <a-form-item label="增加或减少" >
                    <a-select
                            initial-value="add"
                            v-decorator="[
                            'addOrDelete',
                            { rules: [{ required: true, message: '请选择' }] }]"
                    >
                        <a-select-option value="add">
                            增加
                        </a-select-option>
                        <a-select-option value="delete">
                            减少
                        </a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="增加/减少的房间数量">
                    <a-input
                            placeholder="请输入增加/减少的房间数量"
                            v-decorator="[
                            'num',
                            { rules: [{ required: true, message: '请输入增加/减少的房间数量' }] }]"
                    >

                    </a-input>
                </a-form-item>
            </a-form>
        </a-modal>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import {message} from 'ant-design-vue'
    const moment = require('moment')
    export default {
        name: "changeRoomNumModal",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
            }
        },
        props:{
            roomInfo:{},
        },

        computed: {
            ...mapGetters([
                'changeRoomNumModalVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'selectRestoreMethodModal'});
        },
        methods: {
            ...mapMutations([
                "set_changeRoomNumModalVisible",
            ]),
            ...mapActions([
                "updateRoomNum",
            ]),
            handelCancel() {
                this.form.resetFields();
                this.set_changeRoomNumModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if(!err){
                        let addOrDelete = this.form.getFieldValue('addOrDelete')
                        let num = Number(this.form.getFieldValue('num'))
                        if(num < 0){
                            message.error("非法输入")
                            return
                        }
                        if(addOrDelete == "delete"){
                            num = -num
                        }
                        if(this.roomInfo.curNum + num < 0 || this.roomInfo.curNum + num > this.roomInfo.total){
                            message.error("非法输入")
                            return;
                        }
                        let transRoomType
                        switch(this.roomInfo.roomType){
                            case "大床房": transRoomType = "BigBed"
                                break;
                            case "双床房": transRoomType = "DoubleBed"
                                break;
                            case "家庭房": transRoomType = "Family"
                                break;

                        }

                        const data = {
                            hotelId: Number(this.roomInfo.hotelId),
                            roomType: transRoomType,
                            num: num,
                        }
                        this.updateRoomNum(data)
                        //this.$emit('getChild', 1)
                        this.form.resetFields();
                        this.set_changeRoomNumModalVisible(false)
                    }

                })
            },
        },

    }
</script>

<style scoped>

</style>