<template>
    <div class="manageHotel-wrapper">
        <!--a-tabs @change="onTabChange-->
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <!--a-table
                   :columns="columns1"
                   :dataSource="[targetHotel]"
                   bordered
               >
                   <span slot="action" slot-scope="record">
                       <a-button type="primary" size="small" @click="addRoom(record)">录入房间</a-button>
                       <a-divider type="vertical"></a-divider>
                       <a-button type="info" size="small" @click="showCoupon(record)">优惠策略</a-button>
                       <a-divider type="vertical"></a-divider>
                   </span>
               </a-table-->
                <!--
                <div class="hotel-info">
                    <a-card style="width: 240px">
                        <img
                                alt="example"
                                src="@/assets/cover.jpeg"
                                slot="cover"
                                referrerPolicy="no-referrer"
                        />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="targetHotel.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ targetHotel.name }}</span>
                        </div>
                        <div class="items" v-if="targetHotel.bizRegion">
                            <span class="label">酒店商圈：</span>
                            <span class="value">{{ targetHotel.bizRegion }}</span>
                        </div>
                        <div class="items" v-if="targetHotel.address">
                            <span class="label">地址</span>
                            <span class="value">{{ targetHotel.address }}</span>
                        </div>
                        <div class="items" v-if="targetHotel.rate">
                            <span class="label">评分:</span>
                            <span class="value">{{ targetHotel.rate }}</span>
                        </div>
                        <div class="items" v-if="targetHotel.hotelStar">
                            <span class="label">星级:</span>
                            <a-rate style="font-size: 15px" :value="calHotelStar(targetHotel.hotelStar)" disabled allowHalf/>
                        </div>
                        <div class="items" v-if="targetHotel.description">
                            <span class="label">酒店简介:</span>
                            <span class="value">{{ targetHotel.description }}</span>
                        </div>
                        <a-button type="primary" size="small" @click="addRoom(targetHotel)">录入房间</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="showCoupon(targetHotel)">优惠策略</a-button>
                        <a-divider type="vertical"></a-divider>
                    </div>

                </div>
                -->

                <a-form :form="form" style="margin-top: 30px" v-if="targetHotel.name">

                    <a-form-item label="酒店名称" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <span>{{ targetHotel.name }}</span>
                    </a-form-item>

                    <a-form-item label="酒店商圈" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-select
                                v-decorator="['bizRegion',{ rules: [{ required: true, message: '请选择酒店星级' }] }]"
                                v-if="modify"
                        >
                            <a-select-option :value="item" v-for="item of bizRegionList" :key="item.index">
                                {{item}}
                            </a-select-option>
                        </a-select>
                        <span v-else>{{ targetHotel.bizRegion }}</span>
                    </a-form-item>

                    <a-form-item label="地址" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请填写地址"
                                v-decorator="['address', { rules: [{ required: true, message: '请输入地址' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ targetHotel.address }}</span>
                    </a-form-item>

                    <a-form-item label="评分" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ targetHotel.rate }}</span>
                    </a-form-item>

                    <a-form-item label="星级" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-select
                                v-decorator="['hotelStar', { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                                v-if="modify"
                        >
                            <a-select-option value="Three">三星级</a-select-option>
                            <a-select-option value="Four">四星级</a-select-option>
                            <a-select-option value="Five">五星级</a-select-option>
                        </a-select>
                        <a-rate v-else style="font-size: 15px" :value="calHotelStar(targetHotel.hotelStar)" disabled
                                allowHalf/>
                    </a-form-item>

                    <a-form-item label="酒店简介" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请输入简介"
                                v-decorator="['description', { rules: [{ required: true, message: '请输入简介' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ targetHotel.description }}</span>
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
                    <!--
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }">
                        <a-button v-if="targetHotel.name" type="primary" @click="addRoom(targetHotel)">录入房间</a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }">
                        <a-button type="info" @click="showCoupon(targetHotel)">优惠策略</a-button>
                    </a-form-item>
                    -->
                </a-form>
                <a-row v-if="targetHotel.name" >
                    <a-col :span="24" :style="{ textAlign: 'left' }">
                        <a-button type="info"  @click="showCoupon(targetHotel)">优惠策略</a-button>
                        <a-button type="info" :style="{ marginLeft: '10px' }" @click="showService">酒店服务</a-button>
                    </a-col>
                </a-row>
            </a-tab-pane>

            <a-tab-pane tab="房间管理" key="2">
                <div style="width: 100%; text-align: right; margin:20px 0" v-if="targetHotel.name">
                    <a-button type="primary" @click="addRoom(targetHotel)">
                        <a-icon type="plus"/>
                        录入房间
                    </a-button>
                </div>
                <a-table
                        :columns="columns4"
                        :dataSource="targetHotel.rooms"
                        v-if="targetHotel.name"
                        bordered>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" @click="changeAvailableRoomNum(record)">
                            修改房间数量
                        </a-button>
                    </span>

                </a-table>

            </a-tab-pane>


            <a-tab-pane tab="订单管理" key="3">
                <a-table
                        :columns="columns2"
                        :dataSource="targetHotelOrderList"
                        bordered
                        v-if="targetHotel.name"
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
                        <a-button type="primary" size="small" @click="showAndChangeOrderState(record)">订单详情</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该订单吗？"
                                @confirm="deleteOrder(record)"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState=='已撤销' || record.orderState=='已退房'"
                        >
                            <a-button type="danger" size="small">删除订单</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="合作企业" key="4">
                <div style="width: 100%; text-align: right; margin:20px 0" v-if="targetHotel.name">
                    <a-button type="primary" @click="addCooperativeCompany">
                        <a-icon type="plus"/>
                        添加合作企业
                    </a-button>
                </div>
                <a-table
                        :columns="columns3"
                        :dataSource="targetHotel.cooperativeCompanies"
                        bordered
                        v-if="targetHotel.name"
                >
                    <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定想删除该合作企业吗？"
                                @confirm="deleteCompany(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除合作企业</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

        </a-tabs>
        <!--AddHotelModal></AddHotelModal-->
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <!--ShowOrderDetailModal :current-order="currentOrder"></ShowOrderDetailModal-->
        <ShowOrderDetailModal></ShowOrderDetailModal>
        <AddCooperativeCompanyModal></AddCooperativeCompanyModal>
        <!--ChangeRoomNumModal :room-info="curRoomRecord" @getChild="onFormSubmit"></ChangeRoomNumModal-->
        <ChangeRoomNumModal :room-info="curRoomRecord" ></ChangeRoomNumModal>
        <Service></Service>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    //import AddHotelModal from './components/addHotelModal'
    import AddRoomModal from './components/addRoomModal'
    import Coupon from './components/coupon'
    import ShowOrderDetailModal from "../user/components/showOrderDetailModal";
    import AddCooperativeCompanyModal from "./components/addCooperativeCompanyModel";
    import roomList from "../hotel/components/roomList";
    import ChangeRoomNumModal from "./components/changeRoomNumModal";
    import Service from "./components/service";

    const moment = require('moment')
    /*
    const columns1 = [
        {
            title: '酒店名',
            dataIndex: 'name',
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar'
        },
        {
            title: '评分',
            dataIndex: 'rate',
        },
        {
            title: '简介',
            dataIndex: 'description',
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: { customRender: 'action' },
        },
      ];
     */
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
        /*
        {
            title: '状态',
            dataIndex: 'orderState',
            scopedSlots: { customRender: 'orderState' }
        },
         */
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns3 = [
        {
            title: '企业名称',
            dataIndex: 'companyName',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns4 = [
        {
            title: '房型',
            dataIndex: 'roomType',
            key: 'roomType',
        },
       /* {
            title: '床型',
            dataIndex: 'bedType',
            key: 'bedType',
        },
        {
            title: '早餐',
            dataIndex: 'breakfast',
            key: 'breakfast',
        },
        {
            title: '入住人数',
            key: 'peopleNum',
            dataIndex: 'peopleNum',
        },*/
        {
            title: '房价',
            key: 'price',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'}
        },
        {
            title: '总房间数',
            key: 'total',
            dataIndex: 'total',
        },
        {
            title: '空房间数',
            key: 'curNum',
            dataIndex: 'curNum',
        },
        {
            title: 'Action',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'manageHotel',
        data() {
            return {
                modify: false,
                formLayout: 'horizontal',
                pagination: {},
//            columns1,
                columns2,
                columns3,
                columns4,
                form: this.$form.createForm(this, {name: 'manageHotel'}),
                //和info.vue一样,为了方便给子组件传递参数，增加了currentOrder参数，当点击查看订单按钮的时候，这个参数被设置为当前订单
                //只是因为showOrderModalDetail的父组件是manageHotel.vue，所以也增加了这个data
                //currentOrder: Object,
                curRoomRecord: {}, //3.7方便点击的时候props传递当前一行的房间数据
            }
        },
        components: {
            Service,
            ChangeRoomNumModal,
            AddCooperativeCompanyModal,
            //AddHotelModal,
            AddRoomModal,
            Coupon,
            ShowOrderDetailModal,
        },
        computed: {
            ...mapGetters([
                //'orderList',
                //'hotelList',
                'userId',
                'addRoomModalVisible',
                'activeHotelId',
                'couponVisible',
                'targetHotel',
                'targetHotelOrderList',
                'bizRegionList',
                'roomList',
                'serviceVisible',
            ]),

        },
        async mounted() {
            await this.getHotelList()
            await this.getAllOrders()
            await this.getTargetHotel(this.userId)
            await this.getTargetHotelOrderList()
            await this.getAllBizRegions()
            await this.getRoomList()
            //this.calCurHotelRoomList()
        },

        methods: {
            ...mapMutations([
                //'set_addHotelModalVisible',
                'set_addRoomModalVisible',
                'set_couponVisible',
                'set_activeHotelId',
                "set_hotelList",
                "set_showOrderDetailModalVisible",
                "set_addCooperativeCompanyVisible",
                "set_changeRoomNumModalVisible",
                "set_serviceVisible",
            ]),
            ...mapActions([
                'getHotelList',
                'getAllOrders',
                'getHotelCoupon',
                // "deleteHotelM",
                'deleteOrderM',
                'deleteCooperativeCompany',
                'updateCurrentOrder',
                'getTargetHotel',
                'getTargetHotelOrderList',
                "getAllBizRegions",
                'updateHotelInfo',
                "getRoomList",
            ]),
            calHotelStar(hotelStar) {
                switch (hotelStar) {
                    case "Three":
                        return 3;
                    case "Four":
                        return 4;
                    case "Five":
                        return 5;
                    default:
                        return 0;
                }
            },
            /*
            addHotel() {
                this.set_addHotelModalVisible(true)
            },

             */
            addRoom(record) {
                this.set_activeHotelId(record.id)
                this.set_addRoomModalVisible(true)
            },
            showCoupon(record) {
                this.set_activeHotelId(record.id)
                this.set_couponVisible(true)
                this.getHotelCoupon()
            },

            showService(){
                this.set_serviceVisible(true)
            },

            addCooperativeCompany() {
                this.set_addCooperativeCompanyVisible(true);
            },

            /*
            deleteHotel(record){
                //this.deleteHotel(record.id)
                //console.log(record.id)
                //console.log(hotelList)
                //console.log(dataSource)
                this.deleteHotelM(record.id)
                //这里还是用了刷新。。。
                //location.reload()

            },

             */
            deleteOrder(record) {
                //console.log(record.id)
                this.deleteOrderM(record.id) //不这样命名的话会递归
            },
            deleteCompany(record) {
                const data = {
                    hotelId: this.targetHotel.id,
                    companyName: record.companyName,
                }
                this.deleteCooperativeCompany(data)
            },

            //显示订单详细信息，并且允许修改订单状态，所以和普通用户不同，这里的每一次查看都要从后端获取数据，以保持数据最新
            showAndChangeOrderState(record) {
                this.set_showOrderDetailModalVisible(true)
                this.updateCurrentOrder(record.id)
            },

            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            address: this.form.getFieldValue('address'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            description: this.form.getFieldValue('description'),
                        }
                        this.updateHotelInfo(data).then(() => {
                            this.modify = false
                        })
                    }
                });
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'bizRegion': this.targetHotel.bizRegion,
                        'address': this.targetHotel.address,
                        'hotelStar': this.targetHotel.hotelStar,
                        'description': this.targetHotel.description,
                    })
                }, 0)
                this.modify = true
            },
            cancelModify() {
                this.modify = false
            },
            /*
            calCurHotelRoomList(){//计算当前酒店的房间信息
                let targetHotelId = this.targetHotel.id
                this.curHotelRoomList = this.roomList.filter(function (item) {
                    return item.hotelId == targetHotelId
                })
                //console.log("roomList")
                //console.log(this.roomList)
                console.log("after filter")
                console.log(this.curHotelRoomList)
            },


            onTabChange(activeKey){ //在录入房间之后，更新了roomList，并在此处重新计算当前酒店的房间信息
                if(activeKey == 2){
                    this.calCurHotelRoomList()
                }
            },

             */
            changeAvailableRoomNum(record){
                this.set_changeRoomNumModalVisible(true)
                this.curRoomRecord = record
            },
            /*
            onFormSubmit(value){
                console.log("收到子组件")
                this.getRoomList()
                console.log("roomListAfterSubmit")
                console.log(this.roomList)
                //this.calCurHotelRoomList()
            },

             */

        },
    }
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
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
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<!--
<style lang="less">
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                .label{
                    margin-right: 10px;
                    font-size: 18px;
                }
                .value {
                    margin-right: 15px
                }
            }
        }
    }
</style>
-->