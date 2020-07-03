<template>
    <a-modal
            :visible="addRoomModalVisible"
            title="录入客房"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="房型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'roomType', 
                    { rules: [{ required: true, message: '请选择房型' }] }]"
                >
                    <a-select-option value="BigBed">大床房</a-select-option>
                    <a-select-option value="DoubleBed">双床房</a-select-option>
                    <a-select-option value="Family">家庭房</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="房间数量" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写房间数量"
                        v-decorator="['roomNum', { rules: [{ required: true, message: '请输入房间数量' },
                         {validator: (_, value, callback) => { //检验输入，输入为正整数，且不以0开头
                            if(value.length == 0){
                                return callback()
                            }
                            let reg = /^[1-9][0-9]*$/
                            if(!reg.test(value)){
                                return callback('非法输入！');
                            }
                            return callback()
                        }}
                        ] }]"
                />
            </a-form-item>
            <a-form-item label="原始价格" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写原始价格"
                        v-decorator="['price', { rules: [{ required: true, message: '请输入原始价格' },
                        {validator: (_, value, callback) => { //检验输入，输入为正整数或小数
                            if(value.length == 0){
                                return callback()
                            }
                            let reg =  /^[0-9]+([.]{1}[0-9]+){0,1}$/
                            if(!reg.test(value)){
                                return callback('非法输入！');
                            }
                            return callback()
                        }}
                        ] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: 'addRoomModal',
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
        computed: {
            ...mapGetters([
                'addRoomModalVisible',
                'activeHotelId'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addRoomModal'});
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addRoomModalVisible',
                'set_addRoomParams'
            ]),
            ...mapActions([
                'addRoom'
            ]),
            cancel() {
                this.set_addRoomModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            roomType: this.form.getFieldValue('roomType'),
                            price: Number(this.form.getFieldValue('price')),
                            total: Number(this.form.getFieldValue('roomNum')),
                            curNum: Number(this.form.getFieldValue('roomNum')),
                            hotelId: this.activeHotelId,
                        }
                        this.set_addRoomParams(data)
                        this.addRoom()
                        this.form.resetFields()
                    }
                });
            },
        }
    }
</script>