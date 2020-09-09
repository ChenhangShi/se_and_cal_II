<template>
    <div class="salesStrategy-wrapper">
        <a-tabs>
            <a-tab-pane tab="促销策略" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addGlobalCoupon">
                        <a-icon type="plus"/>
                        添加全局优惠券
                    </a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="globalCouponList"
                        bordered
                >
                    <a-tag color="red" slot="couponName" slot-scope="text">{{text}}</a-tag>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="异常订单情况" key="2">
                <a-table
                        :columns="columns2"
                        :dataSource="unusualOrderList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <a-tag slot="orderState" color="red" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-divider type="vertical"></a-divider>
                            <a-button type="danger" size="small" @click="annualOrderForUser(record)">撤销异常订单</a-button>
                    </span>
                </a-table>

            </a-tab-pane>
            <a-tab-pane tab="信用充值" key="3">
                <a-table
                        :columns="columns3"
                        :dataSource="userList"
                        bordered
                >
                    <!--span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span-->
                    <span slot="action" slot-scope="text, record">
                            <a-button type="primary" @click="addCreditForUser(record)">充值</a-button>
                    </span>

                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="会员等级制管理" key="4">
                <a-table
                    :columns="columns4"
                    :dataSource="memberLevelInfo"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" @click="changeLevelRules(record)">
                            修改
                        </a-button>
                    </span>

                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddCoupon></AddCoupon>
        <SelectRestoreMethodModal></SelectRestoreMethodModal>
        <AddCreditModal :curUserId="curUserId"></AddCreditModal>
        <ChangeLevelRulesModal :levelRecord="curLevelRecord" :memberLevelInfo="memberLevelInfo" :disabled="changeLevelRulesDisabled" v-on:getFromSon="getFromSon"></ChangeLevelRulesModal>
    </div>
</template>

<script>
    /**
     * 网站营销人员的网站营销界面
     * */
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddCoupon from '../hotelManager/components/addCouponModel'
    import SelectRestoreMethodModal from "./components/selectRestoreMethodModal";
    import AddCreditModal from "./components/addCreditModal";
    import ChangeLevelRulesModal from "./components/changeLevelRulesModal";
    const columns1 = [
        // 这里定义列表头
        {
            title: '优惠类型',
            dataIndex: 'couponName',
            scopedSlots: {customRender: 'couponName'},
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
            dataIndex: 'discountMoney'
        }
    ];
    const columns2 = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: {customRender: 'roomType'}
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: {customRender: 'checkInDate'}
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: {customRender: 'checkOutDate'}
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
        },
//还是显示了状态，虽然状态一定是"异常"
        {
            title: '状态',
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'}
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns3 = [
        {
            title: '用户ID',
            dataIndex: 'id',
        },
        {
            title: '用户邮箱',
            dataIndex: 'email',
        },
        {
            title: '用户名',
            dataIndex: 'userName',
        },
        /*
        {
            title: '用户密码',
            dataIndex: 'password',
        },

         */
        {
            title: '用户手机号',
            dataIndex: 'phoneNumber',
        },
        {
            title: '信用值',
            dataIndex: 'credit',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns4 = [
        {
            title: "等级",
            dataIndex: 'level',
        },
        {
            title: "信用值",
            dataIndex: 'credit',
        },
        {
            title: '折扣',
            dataIndex: 'discount',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: "salesStrategy",
        data() {
            return {
                columns1,
                columns2,
                columns3,
                columns4,
                curUserId: Number, //由于组件比较简单，直接用props传递数据（信用充值时的用户id）
                //撤销异常订单不用props是因为当时已经在user里面加了一个currentOrder属性，可以直接拿来用
                curLevelRecord: {},//3.7 储存修改的等级制度表格的某列，用于props
                changeLevelRulesDisabled: false, //传递给子组件，如果是等级1，拒绝修改信用值
            }
        },
        components: {
            AddCoupon,
            SelectRestoreMethodModal,
            AddCreditModal,
            ChangeLevelRulesModal,
        },
        computed: {
            ...mapGetters([
                'globalCouponList',
                'userInfo',
                'unusualOrderList',
                'userList',
                'currentOrder',
                'memberLevelInfo',
                'changeLevelRulesModalVisible',
            ])
        },
        async mounted() {
            await this.getGlobalHotelCouponList()
            await this.getUnusualOrderList()
            await this.getUserList()
            await this.getMemberLevelInfo()
        },
        methods: {
            ...mapMutations([
                'set_addCouponVisible',
                "set_selectRestoreMethodModalVisible",
                "set_addCreditModalVisible",
                "set_currentOrder",
                "set_changeLevelRulesModalVisible",

// 另一种解决方案，不需要判断用户类型            'set_activeHotelId',
            ]),
            ...mapActions([
                'getGlobalHotelCouponList',
                "getUnusualOrderList",
                "getUserList",
                "getMemberLevelInfo",
            ]),
            addGlobalCoupon() {
// 另一种解决方案，不需要判断用户类型            this.set_activeHotelId(-1)
                this.set_addCouponVisible(true)
            },
            //3.5
            annualOrderForUser(record) {
                this.set_currentOrder(record)
                this.set_selectRestoreMethodModalVisible(true)
            },
            addCreditForUser(record) { //避免命名冲突,所以加了ForUser
                this.curUserId = record.id
                this.set_addCreditModalVisible(true)
            },
            //3.7
            changeLevelRules(record){ //修改等级规则
                this.curLevelRecord = record
                if(Number(record.level) == 1){
                    this.changeLevelRulesDisabled = true
                }
                this.set_changeLevelRulesModalVisible(true)
            },
            getFromSon(data){//子组件关闭的时候，重新将changeLevelRulesDisabled设置为false
                this.changeLevelRulesDisabled = data
            },
        }
    }
</script>

<style scoped>

</style>