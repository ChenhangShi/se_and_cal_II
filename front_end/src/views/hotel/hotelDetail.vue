<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
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
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span>
                            <span class="value">{{ currentHotelInfo.rate }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                            <a-rate style="font-size: 15px" :value="calHotelStar(currentHotelInfo.hotelStar)" disabled
                                    allowHalf/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span>
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.services.length>0">
                            <span class="label">服务：</span>
                            <span class="value" v-for="service in currentHotelInfo.services" :key="service.serviceType">{{service.serviceType}}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="用户评价" key="2">
                        <a-list
                                class="comment-list"
                                item-layout="horizontal"
                                :data-source="hotelComments"
                        >
                            <a-list-item slot="renderItem" slot-scope="record">
                                <a-comment>

                                    <a slot="author">{{record.userName}}</a>
                                    <a-avatar slot="avatar" src="./defaultAvatar.png"></a-avatar>
                                    <p slot="content">
                                    <p>
                                        评分:
                                        <a-rate :default-value="record.rate" allowHalf disabled=""></a-rate>
                                    </p>
                                    <p>

                                        评论: {{ record.comment }}
                                    </p>
                                </a-comment>
                            </a-list-item>
                        </a-list>
                    </a-tab-pane>
                    <a-tab-pane tab="我的订单" key="3">
                        <a-table
                                :columns="columns"
                                :dataSource="userOrdersOfCurrentHotel"
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
                        </a-table>

                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
    /**
     * 酒店详情页面
     * 分三栏：房间信息，用户评价，我的订单
     * */
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import RoomList from './components/roomList'

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

    ];
    export default {
        name: 'hotelDetail',
        components: {
            RoomList,
        },
        data() {
            return {

                columns,
            }
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
                'hotelComments',
                'userOrderList',
            ]),
            //计算当前用户在当前酒店的订单，为了渲染我的订单tab
            userOrdersOfCurrentHotel: function () {
                let curHotelId = this.currentHotelInfo.id
                let newList = this.userOrderList.filter(function (item) {
                    return item.hotelId == curHotelId
                })
                return newList
            },
        },
        async mounted() {
            this.set_currentHotelId(Number(this.$route.params.hotelId))
            await this.getHotelById()
            await this.getHotelComments()
            await this.getUserOrders()
        },
        beforeRouteUpdate(to, from, next) {
            this.set_currentHotelId(Number(to.params.hotelId))
            this.getHotelById()
            this.getHotelComments()
            next()
        },
        methods: {
            ...mapMutations([
                'set_currentHotelId',
            ]),
            ...mapActions([
                'getHotelById',
                "getHotelComments",
                "getUserOrders"
            ]),
            // 翻译hotel对象中的星级，便于渲染rate组件
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
/*            setUserOrdersOfCurrentHotel() {
                //this.getUserOrders()
                let curHotelId = this.currentHotelInfo.id
                let newList = this.userOrderList.filter(function (item) {
                    return item.hotelId == curHotelId
                })
                this.userOrdersOfCurrentHotel = newList
                //console.log("当前酒店，当前用户的订单：")
                //console.log(this.userOrdersOfCurrentHotel)
            },*/
        }
    }
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
    }

    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;

        .info {
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;

            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;

                .label {
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