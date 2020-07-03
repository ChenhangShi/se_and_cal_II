<template>
    <a-modal
            :visible="addCouponVisible"
            title="添加优惠策略"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'type',
                    { rules: [{ required: true, message: '请选择优惠券类型' }] }]"
                        @change="changeType"
                >
                    <a-select-option value='1' v-if="this.userInfo.userType=='HotelManager'">生日特惠</a-select-option>
                    <a-select-option value='2' v-if="this.userInfo.userType=='HotelManager'">多间特惠</a-select-option>
                    <a-select-option value='3'>满减特惠</a-select-option>
                    <a-select-option value='4'>限时特惠</a-select-option>
                    <a-select-option value="5" v-if="this.userInfo.userType=='HotelManager'">企业优惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写券名"
                        v-decorator="['name', { rules: [{ required: true, message: '请填写券名' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写优惠简介"
                        v-decorator="['description', { rules: [{ required: true, message: '请填写优惠简介' }] }]"
                />
            </a-form-item>
            <a-form-item label="达标金额" v-if="discountType==3">
                <a-input
                        placeholder="请填写达标金额"
                        v-decorator="['targetMoney', { rules: [{ required: true, message: '请填写达标金额' }] }]"
                />
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="discountType==3 || discountType == 2">
                <a-input
                        placeholder="请填写优惠金额"
                        v-decorator="['discountMoney', { rules: [{ required: true, message: '请填写优惠金额' },
                        {validator: (_, value, callback) => { //检验输入，输入为正整数和小数
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
            <a-form-item v-bind="formItemLayout" label="持续时间区间" v-if="discountType == 4">
                <a-range-picker
                        format="YYYY-MM-DD"

                        v-decorator="[
                        'date',
                        {
                            rules: [{ required: true, message: '请选择优惠时间区间' }]
                        }
                    ]"
                        :placeholder="['优惠开始日期','优惠结束日期']"
                />
            </a-form-item>
            <a-form-item label="折扣数" v-bind="formItemLayout"
                         v-if="discountType==1 ||discountType==4 || discountType== 5" min="0" max="1">
                <a-input
                        placeholder="请填写折扣数,例如0.88表示8.8折"
                        v-decorator="['discount', { rules: [{ required: true, message: '请填写优惠金额' },
                        {validator: (_, value, callback) => { //检验输入，输入为小于等于1的整数或小数
                                if(value.length == 0){
                                    return callback()
                                }
                                let reg =  /^[0-9]+([.]{1}[0-9]+){0,1}$/
                                if(!reg.test(value) || Number(value)>1){
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
        name: 'addCouponModal',
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
                discountType: Number
            }
        },
        computed: {
            ...mapGetters([
                'activeHotelId',
                'addCouponVisible',
                'userInfo',
            ]),
            /*
            discountType: function () {
                return Number(this.form.getFieldValue('type'))
            }

             */
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, {name: 'addCouponModal'});
        },
        created() {
            if (this.userInfo.userId == null) {
                if (sessionStorage.getItem("userId")) {
                    this.set_userId(Number(sessionStorage.getItem("userId")))
                    this.getUserInfo()
                }

            }

        },
        mounted() {
            //this.getUserInfo()
            //console.log(this.userInfo)
        },
        methods: {
            ...mapMutations([
                'set_addCouponVisible',
                "set_userId",
            ]),
            ...mapActions([
                // addHotelCoupon：添加酒店策略接口
                'addHotelCoupon',
                'getUserInfo',
            ]),
            cancel() {
                this.set_addCouponVisible(false)
            },
            changeType(v) {
                this.discountType = v
            },
            handleSubmit(e) {
                e.preventDefault();
                /*
                const name = this.form.getFieldValue('name')
                const description= this.form.getFieldValue('description')
                const  type= Number(this.form.getFieldValue('type'))
                const startDate =  (this.form.getFieldValue('date')[0]).format('YYYY-MM-DD')
                const discount = Number(this.form.getFieldValue('discount'))
                console.log(name)
                console.log(description)
                console.log(type)
                console.log(startDate)
                console.log(discount)

                 */


                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const curType = Number(this.form.getFieldValue('type'))
                        let data = null
                        if (curType == 1) {
                            data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                hotelId: Number(this.activeHotelId),
                                status: 1,
                                discount: Number(this.form.getFieldValue('discount')),
                            }
                        } else if (curType == 2) {
                            data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: Number(this.activeHotelId),
                                status: 1,
                            }
                        } else if (curType == 3) {
                            data = {
                                // 这里添加接口参数
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                targetMoney: Number(this.form.getFieldValue('targetMoney')),
                                discountMoney: Number(this.form.getFieldValue('discountMoney')),
                                hotelId: Number(this.activeHotelId),
                                status: 1,
                            }
                        } else if (curType == 4) {
                            data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                hotelId: Number(this.activeHotelId),
                                status: 1,
                                startTime: String((this.form.getFieldValue('date')[0]).format('YYYY-MM-DD')),
                                endTime: String((this.form.getFieldValue('date')[1]).format('YYYY-MM-DD')),
                                discount: Number(this.form.getFieldValue('discount')),
                            }
                        } else if (curType == 5) {
                            data = {
                                name: this.form.getFieldValue('name'),
                                description: this.form.getFieldValue('description'),
                                type: Number(this.form.getFieldValue('type')),
                                hotelId: Number(this.activeHotelId),
                                status: 1,
                                discount: Number(this.form.getFieldValue('discount')),
                            }
                        }
                        if (this.userInfo.userType == 'Salesman') {
                            data.hotelId = -1
                            //console.log("判断成功")
                        }

                        this.addHotelCoupon(data)
                        this.getUserInfo()
                        this.form.resetFields()
                    }
                });


            },
        }
    }
</script>