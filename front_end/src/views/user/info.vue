<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">

                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写用户名"
                                v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="会员类型" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        {{calMemberType()}}
                    </a-form-item>
                    <a-form-item v-if="memberInfo!=null" label="会员等级" :label-col="{ span: 3 }"
                                 :wrapper-col="{ span: 8, offset: 1 }">
                        {{memberInfo.level}}
                    </a-form-item>
                    <a-form-item v-if="memberInfo!=null&&memberInfo.type==1" label="生日" :label-col="{ span: 3 }"
                                 :wrapper-col="{ span: 8, offset: 1 }">
                        {{memberInfo.birthday}}
                    </a-form-item>
                    <a-form-item v-if="memberInfo!=null&&memberInfo.type==2" label="企业名称" :label-col="{ span: 3 }"
                                 :wrapper-col="{ span: 8, offset: 1 }">
                        {{memberInfo.companyName}}
                    </a-form-item>

                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>

                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请填写手机号"
                                v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }"
                                 v-if="modify">
                        <a-input
                                placeholder="请输入新密码"
                                v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }">
                        <!--只有非会员才能注册会员-->
                        <a-button v-if="memberInfo==null" type="default" @click="registerMember">
                            注册会员
                        </a-button>

                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2">
                <a-table
                        :columns="columns"
                        :dataSource="userOrderList"
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
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrderDetail(record)">查看</a-button>
                        <a-divider type="vertical"
                                   v-if="record.orderState == '已执行' || record.orderState == '已退房'"></a-divider>

                        <a-button type="primary" v-if="record.orderState == '已执行' || record.orderState == '已退房'"
                                  size="small" @click="showCommentModal(record)">评价</a-button>

                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                                title="你确定撤销该笔订单吗？"
                                @confirm="confirmCancelOrder(record.id)"
                                @cancel="cancelCancelOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="信用记录" key="3">
                <a-table
                    :columns="columns2"
                    :dataSource="userCreditRecord"
                    bordered
                ></a-table>
            </a-tab-pane>

            <a-tab-pane tab="会员等级制度" key="4">
                <a-table
                    :columns="columns3"
                    :dataSource="memberLevelInfo"
                    bordered
                ></a-table>
            </a-tab-pane>
        </a-tabs>
        <ShowOrderDetailModal></ShowOrderDetailModal>
        <AddCommentModal></AddCommentModal>
        <RegisterMemberModal></RegisterMemberModal>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import ShowOrderDetailModal from "./components/showOrderDetailModal";
    import AddCommentModal from "./components/addCommentModal";
    import RegisterMemberModal from "./components/registerMemberModal";

    const columns = [
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
            title: '价格',
            dataIndex: 'price',
        },
        {
            title: '状态',
            filters: [{text: '已预订', value: '已预订'},
                {text: '已撤销', value: '已撤销'},
                {text: '已执行', value: '已执行'},
                {text: '已退房', value: '已退房'},
                {text: '异常', value: '异常'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: {customRender: 'orderState'}
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },

    ];
    const columns2 = [
        {
            title: '时间',
            dataIndex: 'time',
        },
        {
            title: '订单号',
            dataIndex: 'orderId',
        },
        {
            title: '动作',
            dataIndex: 'action'
        },
        {
            title: '信用变化',
            dataIndex: 'change',
        },
        {
            title: '信用结果',
            dataIndex: 'result',
        },
    ];
    const columns3 = [
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
        }
    ];
    export default {
        name: 'info',
        data() {
            return {
                modify: false,
                formLayout: 'horizontal',
                pagination: {},
                columns,
                columns2,
                columns3,
                data: [],
                form: this.$form.createForm(this, {name: 'coordinated'}),
                //为了方便给子组件传递参数，增加了currentOrder参数，当点击查看订单按钮的时候，这个参数被设置为当前订单
                //currentOrder: Object,

            }
        },
        components: {
            AddCommentModal,
            ShowOrderDetailModal,
            RegisterMemberModal,
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userOrderList',
                'showOrderDetailModalVisible',
                'currentOrder',
                'memberInfo',
                'registerMemberModalVisible',
                'userCreditRecord',
                'memberLevelInfo',
            ])
        },
        async mounted() {
            await this.getUserInfo()
            await this.getUserOrders()
            await this.getMemberInfo()
            await this.getUserCreditRecord()
            await this.getMemberLevelInfo()
        },
        methods: {
            ...mapActions([
                'getUserInfo',
                'getUserOrders',
                'updateUserInfo',
                'cancelOrder',
                'getMemberInfo',
                "getUserCreditRecord",
                "getMemberLevelInfo",
            ]),
            ...mapMutations([
                "set_showOrderDetailModalVisible",
                //user在订单详情页面不会更改订单信息，所以无需和后端联系，所以不用updateCurrentOrder
                "set_currentOrder",
                "set_addCommentModalVisible",
                'set_registerMemberModalVisible',
            ]),
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            password: this.form.getFieldValue('password')
                        }
                        this.updateUserInfo(data).then(() => {
                            this.modify = false
                        })
                    }
                });
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'userName': this.userInfo.userName,
                        'phoneNumber': this.userInfo.phoneNumber,
                    })
                }, 0)
                this.modify = true
            },
            cancelModify() {
                this.modify = false
            },
            confirmCancelOrder(orderId) {
                this.cancelOrder(orderId)
            },
            cancelCancelOrder() {

            },
            showOrderDetail(record) {
                this.set_showOrderDetailModalVisible(true)
                this.set_currentOrder(record)
                //console.log(this.currentOrder)
            },
            //3.4
            showCommentModal(record) {
                this.set_addCommentModalVisible(true)
                this.set_currentOrder(record)
            },
            //3.4.5用于在页面上显示信息
            calMemberType() {
                if (this.memberInfo == null)
                    return "非会员";
                else if (Number(this.memberInfo.type) == 1)
                    return "普通会员";
                else if (Number(this.memberInfo.type) == 2)
                    return "企业会员";
            },
            registerMember() {
                this.set_registerMemberModalVisible(true)
            },


        }
    }
</script>
<style scoped lang="less">
    .info-wrapper {
        padding: 50px;

        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>