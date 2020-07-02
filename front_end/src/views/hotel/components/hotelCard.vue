<template>
    <a-card hoverable class="hotelCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
        <img
                alt="example"
                src="@/assets/cover.jpeg"
                slot="cover"
                referrerPolicy="no-referrer"
        />
        <a-tooltip :title="hotel.title" placement="top">
            <a-card-meta :title="hotel.name">
                <template slot="description">
                    <a-rate style="font-size: 15px" :value="hotel.rate" disabled allowHalf/>
                    {{hotel.rate}}分
                    <a-rate style="font-size: 15px" :value="calHotelStar(hotel.hotelStar)" disabled allowHalf/>
                    {{transHotelStar(hotel.hotelStar)}}
                    <div :style="{ marginTop: '3px' }">
                        <a-tag v-if="usual" color="blue">
                            正常
                        </a-tag>
                        <a-tag v-if="annulled" color="blue">
                            已撤销
                        </a-tag>
                        <a-tag v-if="unusual" color="red">
                            异常
                        </a-tag>
                    </div>
                    <div></div>
                    <div>
                        <a-button type="primary" @click.stop="showRoomListModal(hotel.id)" :style="{ marginTop: '5px' }"> <!--阻止冒泡事件哈哈哈哈哈哈-->
                            预定
                        </a-button>
                    </div>
                </template>
            </a-card-meta>
        </a-tooltip>
        <RoomListModal></RoomListModal>
    </a-card>
</template>
<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import RoomListModal from "./roomListModal";

    export default {
        name: '',
        props: {
            hotel: {}
        },
        data() {
            return {
                /*
                usual: false,
                unusual: false,
                annulled: false,

                 */
            }
        },
        components: {
            RoomListModal,
        },
        async mounted() {
            await this.getUserOrders()

            /*
            this.isUsual()
            this.isUnusual()
            this.isAnnulled()

             */

        },
        computed: {
            ...mapGetters([
                'userOrderList',
                "currentHotelInfo",
            ]),
            usual: {
                get: function () {
                    let curHotelId = this.hotel.id
                    let res = false
                    let ls1 = this.userOrderList
                    for (let i = 0; i < ls1.length; i++) {
                        let curElem = ls1[i]
                        if (curElem.hotelId == curHotelId && (curElem.orderState == '已预订' || curElem.orderState == '已执行' || curElem.orderState == '已退房')) {
                            res = true
                        }
                    }
                    return res
                },
                set: function (newVal) {  //搞错了，其实不用写set

                }
            },
            unusual: {
                get: function() {
                    let curHotelId = this.hotel.id
                    let res = false
                    let ls1 = this.userOrderList
                    for (let i = 0; i < ls1.length; i++) {
                        let curElem = ls1[i]
                        if (curElem.hotelId == curHotelId && curElem.orderState == '异常') {
                            res = true
                        }
                    }
                    return res
                },
                set: function(newVal){

                }
            },
            annulled: {
                get: function () {
                    let curHotelId = this.hotel.id
                    let res = false
                    let ls1 = this.userOrderList
                    for (let i = 0; i < ls1.length; i++) {
                        let curElem = ls1[i]
                        if (curElem.hotelId == curHotelId && curElem.orderState == '已撤销') {
                            res = true
                        }
                    }
                    return res
                },
                set: function (newVal) {

                }
            },
        },
        methods: {
            ...mapActions([
                "getUserOrders",
                "getHotelById",
            ]),
            ...mapMutations([
                "set_roomListModalVisible",
                "set_currentHotelId",
            ]),
            showRoomListModal(hotelId) {
                this.set_currentHotelId(hotelId)
                this.getHotelById()
                this.set_roomListModalVisible(true)
            },
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
            transHotelStar(hotelStar) {//为了把星级翻译成中文
                switch (hotelStar) {
                    case "Three":
                        return "三星级";
                    case "Four":
                        return "四星级";
                    case "Five":
                        return "五星级";
                    default:
                        return "暂无数据";
                }
            }
        }
    }
</script>
<style scoped lang="less">
    .hotelCard {
        margin: 10px 10px;
        min-width: 180px;
        max-height: 403px; //原先是350
        img {
            height: 250px;
        }
    }
</style>

<style lang="less">
    .hotelCard {
        .ant-card-body {
            padding: 12px
        }
    }

    .ant-card-hoverable:hover {
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.4)
    }

</style>