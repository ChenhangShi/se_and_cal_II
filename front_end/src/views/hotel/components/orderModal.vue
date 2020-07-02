<template>
    <a-modal
            :visible="orderModalVisible"
            title="预定详情"
            cancelText="取消"
            okText="下单"
            @cancel="cancelOrder"
            @ok="handleSubmit"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="房型信息">
                <span>{{ currentOrderRoom.roomType }}</span>
            </a-form-item>
            <!--
            <a-form-item v-bind="formItemLayout" label="入住人姓名">
                <a-input
                    v-decorator="[
                        'clientName',
                        { rules: [{required: true, message: '请填写入住人姓名', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                    v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请填写入住人联系手机', }] }
                    ]"
                />
            </a-form-item>
            -->


            <a-form-item v-bind="formItemLayout" label="入住日期">
                <a-range-picker
                        format="YYYY-MM-DD"
                        @change="changeDate"
                        v-decorator="[
                        'date', 
                        {
                            rules: [{ required: true, message: '请选择入住时间' }]   
                        }
                    ]"
                        :placeholder="['入住日期','退房日期']"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人数">
                <a-select
                        v-decorator="[
                        'peopleNum',
                        { rules: [{ required: true, message: '请选择入住人数' }] },
                    ]"
                        placeholder="请选择入住人数"
                        @change="changePeopleNum"
                >
                    <a-select-option :value="1">
                        1
                    </a-select-option>
                    <a-select-option :value="2">
                        2
                    </a-select-option>
                    <a-select-option :value="3">
                        3
                    </a-select-option>
                    <a-select-option :value="4">
                        4
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有无儿童">
                <a-radio-group
                        v-decorator="[
                        'haveChild',
                        { rules: [{required: true, message: '请选择有无儿童入住', }] }
                    ]"
                >
                    <a-radio :value="1">有</a-radio>
                    <a-radio :value="0">无</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间数">
                <a-select
                        v-decorator="[
                        'roomNum',
                        { rules: [{ required: true, message: '请选择房间数' }] },
                    ]"
                        placeholder="请选择房间数"
                        @change="changeRoomNum"
                >
                    <a-select-option :value="1">
                        1
                    </a-select-option>
                    <a-select-option :value="2">
                        2
                    </a-select-option>
                    <a-select-option :value="3">
                        3
                    </a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间单价">
                <span>¥{{ currentOrderRoom.price }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="总价">
                <span>￥{{ totalPrice }}</span>
            </a-form-item>
            <a-divider></a-divider>
            <a-form-item v-bind="formItemLayout" v-if="this.memberInfo != null"  label="会员折扣">{{"     您是" + this.memberInfo.level + "级会员，价格是原来的" + memberLevelInfo[memberInfo.level - 1].discount + "倍"}}</a-form-item>
            <h2 v-if="orderMatchCouponList.length>0">优惠</h2>
            <a-radio-group v-model="picked" @change="onchange" v-if="orderMatchCouponList.length>0">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="resetPicked">撤销选择</a-button>
                </div>
                <a-table
                        :columns="columns"
                        :dataSource="orderMatchCouponList"
                        :showHeader="false"
                        bordered
                        v-if="orderMatchCouponList.length>0"
                >
                    <a-radio
                            slot="id"
                            slot-scope="record"
                            :value="record"
                    >
                    </a-radio>
                </a-table>
            </a-radio-group>
            <a-form-item v-bind="formItemLayout" label="结算后总价">
                <span>￥{{ finalPriceToFixed }}</span>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    const moment = require('moment')
    const columns = [
        {
            title: '勾选',
            dataIndex: 'id',
            scopedSlots: {customRender: 'id'}
        },
        {
            title: '优惠类型',
            dataIndex: 'couponName',
            scopedSlots: {customRender: 'couponName'}
        },
        {
            title: '折扣',
            dataIndex: 'discount',
        },

        {
            title: '优惠简介',
            dataIndex: 'description',

        },
        {
            title: '优惠金额',
            dataIndex: 'discountMoney',
        },
    ];
    export default {
        name: 'orderModal',
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
                totalPrice: '',
                columns,
                // checkedList: [],
                picked: -1,
                finalPrice: ''
            }
        },
        computed: {
            ...mapGetters([
                'orderModalVisible',
                'currentOrderRoom',
                'currentHotelId',
                'currentHotelInfo',
                'userId',
                'orderMatchCouponList',
                'userOrderList',
                'memberLevelInfo',
                'memberInfo',
            ]),
            finalPriceToFixed: function () {
                return Number(this.finalPrice).toFixed(2)
            },
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'orderModal'});
        },
        async mounted(){
            await this.getMemberLevelInfo()
            await this.getMemberInfo()
        },
        methods: {
            ...mapMutations([
                'set_orderModalVisible',
                "set_orderMatchCouponList",
            ]),
            ...mapActions([
                'addOrder',
                'getOrderMatchCoupons',
                "getUserOrders",
                "getMemberLevelInfo",
                "getMemberInfo",
            ]),

            resetPicked(){
                this.picked=-1;
                this.onchange();
            },

            cancelOrder() {
                this.form.resetFields()
                this.set_orderMatchCouponList([])
                this.set_orderModalVisible(false)
                this.finalPrice = ''
                this.totalPrice = ''
            },
            confirmOrder() {

            },
            changeDate(v) {
                // 因为0==''为true 当总价为0时跳过 所以改用严格判断
                if (this.totalPrice!=='') {
                    this.totalPrice = this.form.getFieldValue('roomNum') * moment(v[1]).diff(moment(v[0]), 'day') * Number(this.currentOrderRoom.price)
                }
                this.finalPrice= this.calFinalPrice(this.totalPrice)
            },
            changePeopleNum(v) {

            },
            changeRoomNum(v) {
                this.totalPrice = Number(v) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('date')[1]).diff(moment(this.form.getFieldValue('date')[0]), 'day')
                this.finalPrice= this.calFinalPrice(this.totalPrice)
            },
            onchange() {
                this.finalPrice= this.calFinalPrice(this.totalPrice)
                /*
                if (this.checkedList.length > 0) {
                    this.orderMatchCouponList.filter(item => this.checkedList.indexOf(item.id) != -1).forEach(item => this.finalPrice = this.finalPrice - (item.discountMoney > 0 ? item.discountMoney : this.totalPrice * (1 - item.discount)))
                }
                 */

                if (this.picked>=0) {
                    /*
                    this.orderMatchCouponList.filter(item => item.id==this.picked).forEach(item => this.finalPrice = this.finalPrice - (item.discountMoney > 0 ? item.discountMoney : this.totalPrice * (1 - item.discount)))
                     */
                    let item;
                    for(let i in this.orderMatchCouponList){
                        if(this.orderMatchCouponList[i].id==this.picked){
                            item=this.orderMatchCouponList[i]
                            break
                        }
                    }
                    this.finalPrice = item.discountMoney > 0 ? (this.finalPrice - item.discountMoney) : (this.finalPrice * item.discount)
                }
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            hotelId: this.currentHotelId,
                            hotelName: this.currentHotelInfo.name,
                            userId: Number(this.userId),
                            checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                            checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                            roomType: this.currentOrderRoom.roomType == '大床房' ? 'BigBed' : this.currentOrderRoom.roomType == '双床房' ? 'DoubleBed' : 'Family',
                            roomNum: this.form.getFieldValue('roomNum'),
                            peopleNum: this.form.getFieldValue('peopleNum'),
                            haveChild: this.form.getFieldValue('haveChild'),
                            createDate: '',
                            //price: this.checkedList.length > 0 ? this.finalPrice : this.totalPrice
                            price: this.finalPrice,
                        }
                        this.addOrder(data)
                        //this.getUserOrders()
                        this.form.resetFields();
                        this.set_orderMatchCouponList([]);
                        this.totalPrice = '';
                        this.finalPrice = '';
                        // this.checkedList = [];
                        this.picked=-1;
                    }
                });
            },
            calFinalPrice(totalPrice){
                if(this.memberInfo == null){
                    return totalPrice
                }
                let lv = Number(this.memberInfo.level)
                let discount = this.memberLevelInfo[lv-1].discount;
                let res = totalPrice*Number(discount)
                return res.toFixed(2)
            },
        },


        watch: {
            totalPrice(val) {
                let data = {
                    userId: this.userId,
                    hotelId: this.currentHotelId,
                    orderPrice: this.totalPrice,
                    roomNum: this.form.getFieldValue('roomNum'),
                    checkIn: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                    checkOut: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                    create: moment().format('YYYY-MM-DDThh:mm:ss')
                }
                this.getOrderMatchCoupons(data)
            }
        }
    }
</script>