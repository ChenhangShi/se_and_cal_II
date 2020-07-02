<template>
    <div>
        <a-modal
                :visible="showOrderDetailModalVisible"
                title="订单详情"
                @cancel="handleCancel"
        >
            <template slot="footer">
                <a-button key="back" @click="handleCancel">
                    返回
                </a-button>
                <a-button v-if="userInfo.userType=='HotelManager'" key="submit" type="primary" @click="handleSubmit">
                    提交更改
                </a-button>
            </template>
            <div>
                <a-form :form="form" v-bind="formItemLayout">
                    <a-list size="large" bordered>
                        <a-list-item>订单号: {{currentOrder.id}}</a-list-item>
                        <a-list-item>入住日期: {{currentOrder.checkInDate}}</a-list-item>
                        <a-list-item>离店日期: {{currentOrder.checkOutDate}}</a-list-item>
                        <a-list-item>下单用户姓名: {{currentOrder.clientName}}</a-list-item>
                        <a-list-item>创建日期: {{currentOrder.createDate}}</a-list-item>
                        <a-list-item>有无儿童: {{currentOrder.haveChild ? "有" : "无"}}</a-list-item>
                        <a-list-item>酒店名称: {{currentOrder.hotelName}}</a-list-item>
                        <a-list-item>订单状态: {{currentOrder.orderState}}</a-list-item>
                        <a-list-item>入住人数: {{currentOrder.peopleNum}}</a-list-item>
                        <a-list-item>联系人电话: {{currentOrder.phoneNumber}}</a-list-item>
                        <a-list-item v-if="currentOrder.roomType == 'BigBed'">房型: 大床房</a-list-item>
                        <a-list-item v-if="currentOrder.roomType == 'Family'">房型: 家庭房</a-list-item>
                        <a-list-item v-if="currentOrder.roomType == 'DoubleBed'">房型: 双床房</a-list-item>
                        <a-list-item>房间数量: {{currentOrder.roomNum}}</a-list-item>
                        <a-list-item>总价格: ¥{{currentOrder.price}}</a-list-item>
                    </a-list>
                    <a-form-item v-if="changeOrderStateButtonVisible" label="更改订单状态" v-bind="formItemLayout">
                        <a-select
                                v-decorator="[
                    'newOrderState',
                    { rules: [{ required: true, message: '请选择订单类型' }] }]"
                        >
                            <a-select-option v-if="currentOrder.orderState=='已预订' || currentOrder.orderState=='异常'"
                                             value="已执行">已执行
                            </a-select-option>
                            <a-select-option v-if="currentOrder.orderState=='已执行' || currentOrder.orderState=='异常'"
                                             value="已退房">已退房
                            </a-select-option>
                        </a-select>
                    </a-form-item>
                </a-form>
            </div>


        </a-modal>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    const moment = require('moment')
    export default {
        name: "showOrderDetailModal",
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
        props: {


            /*
            currentOrder: {
                type: Object,
                required: true,
            },
            checkInDate: String,
            checkOutDate: String,
            clientName: String,
            createDate: String,
            haveChild: String,
            hotelName: String,
            orderState: String,
            peopleNum: String,
            phoneNumber: String,
            price: String,
            roomNum: String,
            roomType: String,
             */
        },
        computed: {
            ...mapGetters([
                'showOrderDetailModalVisible',
                'currentOrder',
                'userInfo',
            ]),
            changeOrderStateButtonVisible() { //判断当前页面的url是否含有manager，有的话就有权修改订单状态
                let url = window.location.href
                let patt = /manager/i
                if (url.search(patt) == -1) {
                    return false
                }
                return true
            },

        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'showOrderDetailModal'});
        },
        methods: {
            ...mapMutations([
                'set_showOrderDetailModalVisible'
            ]),

            ...mapActions([
                'getAllOrders',
                'changeOrderState'
            ]),
            handleCancel() {
                this.set_showOrderDetailModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        //console.log("开始调用")
                        const data = {
                            id: this.currentOrder.id,
                            newOrderState: this.form.getFieldValue('newOrderState'),
                        }
                        //console.log(data.newOrderState)
                        this.changeOrderState(data)

                        //重置表单，防止重复操作
                        this.form.resetFields()
                    }
                });
            },
        },

    }

</script>
