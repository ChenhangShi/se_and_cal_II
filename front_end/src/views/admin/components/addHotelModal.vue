<template>
    <a-modal
            :visible="addHotelModalVisible"
            title="添加酒店"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名">
                <a-input
                        placeholder="请填写酒店名称"
                        v-decorator="['hotelName', { rules: [{ required: true, message: '请填写酒店名称' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店地址" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店地址"
                        v-decorator="['address', { rules: [{ required: true, message: '请填写酒店地址' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店商圈" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'bizRegion',
                    { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                >
                    <a-select-option :value="item" v-for="item of bizRegionList" :key="item.index">
                        {{item}}
                    </a-select-option>
                </a-select>
            </a-form-item>

            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'hotelStar', 
                    { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                        @change="changeStar"
                >
                    <a-select-option value="Three">三星级</a-select-option>
                    <a-select-option value="Four">四星级</a-select-option>
                    <a-select-option value="Five">五星级</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="手机号" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写手机号"
                        v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' },
                        {validator: (_, value, callback) => { //验证手机号格式
                        if(value.length == 0){
                                return callback()
                            }
                            let patt = /^[0-9]*$/
                            if(value.length!=11 || !patt.test(value)){
                                return callback('手机号格式错误');
                            }
                            return callback()
                        }}
                        ] }]"
                />
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写酒店简介"
                        v-decorator="['description', { rules: [{ required: true, message: '请填写酒店简介' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import {message} from 'ant-design-vue'

    export default {
        name: 'addHotelModal',
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
                'userId',
                'addHotelParams',
                'addHotelModalVisible',
                'bizRegionList',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addHotelModal'});
        },
        async mounted() {
            await this.getAllBizRegions()
        },
        methods: {
            ...mapMutations([
                'set_addHotelParams',
                'set_addHotelModalVisible'
            ]),
            ...mapActions([
                'addHotel',
                "getAllBizRegions",
            ]),
            cancel() {
                this.set_addHotelModalVisible(false)
            },
            changeStar(v) {

            },


            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.form.getFieldValue('hotelName'),
                            description: this.form.getFieldValue('description'),
                            address: this.form.getFieldValue('address'),
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            phoneNum: this.form.getFieldValue('phoneNumber'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            // managerId: Number(this.userId)
                        }
                        /*
                        let patt = /^[0-9]*$/

                        if(data.phoneNum.length != 11 || !patt.test(data.phoneNum)){
                            message.error('手机号格式错误')
                            return
                        }

                         */
                        this.set_addHotelParams(data)
                        this.addHotel()
                    }
                });
            },
        }
    }
</script>