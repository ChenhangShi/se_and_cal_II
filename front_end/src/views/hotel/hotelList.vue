<template>
    <div class="hotelList">
        <div class="sortCheckBoxes">
            <span style="margin-right: 20px"> 请选择排序方式（由高到低）</span>
            <a-checkbox :checked="sortByStar" @change="sortByStarClicked">
                按星级排序
            </a-checkbox>
            <a-checkbox :checked="sortByComment" @change="sortByCommentClicked">
                按评分排序
            </a-checkbox>
        </div>
        <a-divider />
        <div class="selectors">

            <span style="margin-right: 10px">商圈</span>
            <a-select default-value="全部" style="width: 100px" @change="bizRegionChange" class="selector1" size="small">
                <a-select-option value="全部">
                    全部
                </a-select-option>
                <a-select-option :value="item" v-for="item of bizRegionList" :key="item.index">
                    {{item}}
                </a-select-option>
            </a-select>
            <a-divider type="vertical"/>

            <span style="margin-right: 10px">订单类型</span>
            <a-select default-value="全部" style="width: 100px" @change="orderedSelectorChange" class="selector2"
                      size="small">
                <a-select-option value="全部">
                    全部
                </a-select-option>
                <a-select-option value="正常">
                    正常
                </a-select-option>
                <a-select-option value="已撤销">
                    已撤销
                </a-select-option>
                <a-select-option value="异常">
                    异常
                </a-select-option>
            </a-select>
            <a-divider type="vertical"/>

            <span style="margin-right: 10px">房型</span>
            <a-select mode="multiple" style="width: 100px" @change="roomTypeSelectorChange" size="small">
                <a-select-option value="大床房">
                    大床房
                </a-select-option>
                <a-select-option value="双床房">
                    双床房
                </a-select-option>
                <a-select-option value="家庭房">
                    家庭房
                </a-select-option>
            </a-select>
            <a-divider type="vertical"/>

            <span style="margin-right: 10px">价格</span>
            <a-select mode="multiple" style="width: 130px" @change="priceSelectorChange" size="small">
                <a-select-option value="0">
                    小于100元
                </a-select-option>
                <a-select-option value="1">
                    100-199
                </a-select-option>
                <a-select-option value="2">
                    200-299
                </a-select-option>
                <a-select-option value="3">
                    300-399
                </a-select-option>
                <a-select-option value="4">
                    400-499
                </a-select-option>
                <a-select-option value="5">
                    500元以上
                </a-select-option>
            </a-select>
            <a-divider type="vertical"/>

            <span style="margin-right: 10px">星级</span>
            <a-select mode="multiple" style="width: 100px" @change="starSelectorChange" size="small">
                <a-select-option value="Three">
                    三星级
                </a-select-option>
                <a-select-option value="Four">
                    四星级
                </a-select-option>
                <a-select-option value="Five">
                    五星级
                </a-select-option>
            </a-select>
            <a-divider type="vertical"/>
            <span style="margin-right: 10px">评分</span>
            <a-select mode="multiple" style="width: 100px" @change="rateSelectorChange" size="small">
                <a-select-option value="0">
                    1分以下
                </a-select-option>
                <a-select-option value="1">
                    1-1.99
                </a-select-option>
                <a-select-option value="2">
                    2-2.99
                </a-select-option>
                <a-select-option value="3">
                    3-3.99
                </a-select-option>
                <a-select-option value="4">
                    4分以上
                </a-select-option>
            </a-select>
            <a-divider type="vertical"/>

            <span style="margin-right: 10px">有空房</span>
            <a-checkbox @change="emptyRoomSelectorChange">
            </a-checkbox>
            <a-divider />

            <a-input-search @change="searchBoxChange" class="searchBox" size="small" placeholder="输入酒店名称"  enter-button @search="searchHotelByName" />
            <a-divider />

        </div>
        <a-layout>
            <a-layout-content style="min-width: 800px">
                <a-spin :spinning="hotelListLoading">
                    <div class="card-wrapper">
                        <HotelCard :hotel="item" v-for="item in hotelListAfterSelect" :key="item.index"
                                   @click.native="jumpToDetails(item.id)"></HotelCard>
                        <div v-for="item in emptyBox" :key="item.name"
                             class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                        </div>
                        <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1"
                                      @change="pageChange"></a-pagination>
                    </div>
                </a-spin>
            </a-layout-content>
        </a-layout>
    </div>
</template>
<script>
    /**
     * 酒店list，同时也是系统的主界面
     * */
    import HotelCard from './components/hotelCard'
    import {mapActions, mapGetters, mapMutations} from 'vuex'
    import hotel from "../../store/modules/hotel";

    export default {
        name: 'home',
        components: {
            HotelCard
        },
        data() {
            return {
                emptyBox: [{name: 'box1'}, {name: 'box2'}, {name: 'box3'}],
                hotelListAfterSelect: [], //总的筛选结果，是下面几个结果的交集
                hotelListAfterBizRegionSelect: [], //商圈筛选的结果
                hotelListAfterOrderedSelect: [], //是否仅展示预定的酒店 筛选的结果
                roomTypeSelect: [],  //房型筛选结果（不写那么长了）
                priceSelect: [],  //价格区间筛选结果
                starSelect: [], //按照星级筛选
                rateSelect: [], //按照评分区间筛选
                emptyRoomSelect: [], //只查看有空房的酒店
                nameSearch: [], //通过名称搜索酒店


                sortByStar: false, //按照星级排序
                sortByComment: false, //按照评价排序
                roomMap: [],
            }
        },
        async mounted() {
            await this.getHotelList()
            await this.getAllBizRegions()
            await this.getUserOrders()
            await this.getRoomList()
            // 把各个筛选结果初始化为hotelList
            this.hotelListAfterSelect = this.hotelList
            this.hotelListAfterBizRegionSelect = this.hotelList
            this.hotelListAfterOrderedSelect = this.hotelList
            this.roomTypeSelect = this.hotelList
            this.priceSelect = this.hotelList
            this.starSelect = this.hotelList
            this.rateSelect = this.hotelList
            this.emptyRoomSelect = this.hotelList
            this.nameSearch = this.hotelList
            // 通过hotelList和roomList，计算酒店与房间的映射关系，为了筛选有空房的酒店
            this.initRoomMap()
            // console.log("映射关系")
            // console.log(this.roomMap)
        },
        computed: {
            ...mapGetters([
                'hotelList',
                'hotelListLoading',
                'userOrderList',
                'bizRegionList',
                'roomList',
            ]),
        },
        methods: {
            ...mapMutations([
                'set_hotelListParams',
                'set_hotelListLoading',
            ]),
            ...mapActions([
                'getHotelList',
                "getAllBizRegions",
                "getUserOrders",
                "getRoomList",
            ]),

            pageChange(page, pageSize) {
                const data = {
                    pageNo: page - 1
                }
                this.set_hotelListParams(data)
                this.set_hotelListLoading(true)
                this.getHotelList()
                this.getRoomList()
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
            },
            /**
             * 以下几个方法是当筛选条件改变的时候，先根据自己的表单内容筛选酒店，在与其余条件筛选出来的酒店列表求交集
             *
             * */

            // 商圈下拉框改变
            bizRegionChange(keyword) {//商圈下拉框改变时执行
                if (keyword !== '全部') {
                    let newList = this.hotelList.filter(function (item) {
                        return item.bizRegion == keyword;
                    })
                    this.hotelListAfterBizRegionSelect = newList
                } else this.hotelListAfterBizRegionSelect = this.hotelList
                this.setHotelListAfterSelect()//求筛选条件的交集，作为最终结果
            },
            // 订单状态下拉框改变
            orderedSelectorChange(keyword) {//同理，是否仅显示已预订下拉框改变时执行
                if (keyword == '正常') {
                    let orderedHotelIdList = []
                    let ls1 = this.userOrderList
                    for (let i = 0; i < ls1.length; i++) {
                        let curElem = ls1[i]
                        if (curElem.orderState == '已预订' || curElem.orderState == '已执行' || curElem.orderState == '已退房') {
                            orderedHotelIdList.push(curElem.hotelId)
                        }
                    }

                    let newList = this.hotelList.filter(function (item) {
                        return orderedHotelIdList.indexOf(item.id) != -1
                    })
                    this.hotelListAfterOrderedSelect = newList
                }
                else if (keyword == '异常') {
                    let orderedHotelIdList = []
                    let ls1 = this.userOrderList
                    for (let i = 0; i < ls1.length; i++) {
                        let curElem = ls1[i]
                        if (curElem.orderState == '异常') {
                            orderedHotelIdList.push(curElem.hotelId)
                        }
                    }

                    let newList = this.hotelList.filter(function (item) {
                        return orderedHotelIdList.indexOf(item.id) != -1
                    })
                    this.hotelListAfterOrderedSelect = newList
                }
                else if (keyword == '已撤销') {
                    let orderedHotelIdList = []
                    let ls1 = this.userOrderList
                    for (let i = 0; i < ls1.length; i++) {
                        let curElem = ls1[i]
                        if (curElem.orderState == '已撤销') {
                            orderedHotelIdList.push(curElem.hotelId)
                        }
                    }

                    let newList = this.hotelList.filter(function (item) {
                        return orderedHotelIdList.indexOf(item.id) != -1
                    })
                    this.hotelListAfterOrderedSelect = newList
                }
                else {
                    this.hotelListAfterOrderedSelect = this.hotelList
                }
                this.setHotelListAfterSelect()//求交集
            },

            //处理根据房型筛选酒店，注意，roomList中roomType是中文
            roomTypeSelectorChange(k) {

                if (k.length == 0) {
                    this.roomTypeSelect = this.hotelList
                } else {
                    let hotelIdAfterFilter = []
                    for (let i = 0; i < this.roomMap.length; i++) {
                        let cur = this.roomMap[i]
                        let flag = true  //当前酒店的房型是否完全覆盖了k
                        for (let j = 0; j < k.length; j++) {
                            if (cur.roomType.indexOf(k[j]) == -1) {
                                flag = false
                            }
                        }
                        if (flag) {
                            hotelIdAfterFilter.push(cur.id)
                        }
                    }
                    this.roomTypeSelect = this.hotelList.filter(function (item) {
                        return hotelIdAfterFilter.indexOf(item.id) != -1
                    })
                }
                //console.log("房型筛选后的酒店")
                //console.log(this.afterRoomTypeSelect)
                this.setHotelListAfterSelect()
            },

            //处理根据价格筛选酒店
            priceSelectorChange(k) {
                k = k.map(function (elem) {
                    return Number(elem)
                })
                if (k.length == 0) {
                    this.priceSelect = this.hotelList
                } else {
                    let hotelIdAfterFilter = []
                    for (let i = 0; i < this.roomMap.length; i++) {
                        let cur = this.roomMap[i]
                        let flag = false //当前所选的价格区间是否和酒店所有房间的价格区间有重叠
                        for (let j = 0; j < cur.prices.length; j++) {
                            let temp = Math.floor(cur.prices[j] / 100)  //除以100，向下取整，看是否在k里面，当然价格超过600另外判断
                            if (temp >= 6 && k.indexOf(5) != -1) {
                                flag = true
                                break
                            }
                            if (k.indexOf(temp) != -1) {
                                flag = true
                                break
                            }
                        }
                        if (flag) {
                            hotelIdAfterFilter.push(cur.id)
                        }
                    }
                    this.priceSelect = this.hotelList.filter(function (item) {
                        return hotelIdAfterFilter.indexOf(item.id) != -1
                    })
                }
                //console.log("价格区间筛选结果")
                //console.log(this.priceSelect)
                this.setHotelListAfterSelect()
            },

            //处理筛选有空房的酒店
            emptyRoomSelectorChange(e) {
                if (!e.target.checked) {
                    this.emptyRoomSelect = this.hotelList
                } else {
                    let hotelIdAfterFilter = []
                    for (let i = 0; i < this.roomMap.length; i++) {
                        let cur = this.roomMap[i]
                        let temp = cur.curNums.sort()
                        if (temp[temp.length - 1] != 0) {
                            hotelIdAfterFilter.push(cur.id)
                        }
                    }
                    this.emptyRoomSelect = this.hotelList.filter(function (item) {
                        return hotelIdAfterFilter.indexOf(item.id) != -1
                    })
                }
                this.setHotelListAfterSelect()
            },

            //按照星级筛选
            starSelectorChange(k){
                if(k.length == 0){
                    this.starSelect = this.hotelList
                }
                else{
                    this.starSelect = this.hotelList.filter(function (item) {
                        return k.indexOf(item.hotelStar) != -1
                    })
                }
                this.setHotelListAfterSelect()
            },

            //按照评分区间筛选
            rateSelectorChange(k){
                k = k.map(function (item) {
                    return Number(item)
                })
                if(k.length == 0){
                    this.rateSelect = this.hotelList
                }
                else{
                    this.rateSelect = this.hotelList.filter(function (item) {
                        if(item.rate == 5 && k.indexOf(4) != -1){ //5分，特殊处理
                            return true
                        }
                        return k.indexOf(Math.floor(item.rate)) != -1
                    })
                }
                this.setHotelListAfterSelect()
            },

            /**
             * 以下几个方法是搜索酒店功能相关
             * */

            //搜索框清空时调用，把搜索列表清空，防止搜索框清空，界面上什么也没有
            searchBoxChange(e){
                let temp = e.target.value
                if(temp.length == 0){
                    this.nameSearch = this.hotelList
                }
                this.setHotelListAfterSelect()
            },

            //根据酒店名搜索酒店，支持空格，逗号分隔，支持显示多个结果
            searchHotelByName(k){
                let kList = k.split(/ |,|，/) //按照空格，逗号或者中文逗号分割
                console.log(kList)
                console.log(this.hotelList)
                if(k.length == 0){
                    this.nameSearch = this.hotelList
                }
                else{
                    let newList = []
                    for(let i=0;i<this.hotelList.length;i++){
                        let cur = this.hotelList[i]
                        let flag = false
                        for(let j=0;j<kList.length;j++){
                            if(cur.name.indexOf(kList[j])!=-1){
                                flag = true
                            }
                        }
                        if(flag){
                            newList.push(cur)
                        }
                    }
                    this.nameSearch = newList
                }
                this.setHotelListAfterSelect()
            },

            /**
             * 这个方法是把筛选、搜索所得到的8个数组求交集，最终得到综合筛选结果
             * */

            setHotelListAfterSelect() {
                let ls1 = this.hotelListAfterBizRegionSelect
                let ls2 = this.hotelListAfterOrderedSelect
                let ls3 = this.roomTypeSelect
                let ls4 = this.priceSelect
                let ls5 = this.emptyRoomSelect
                let ls6 = this.starSelect
                let ls7 = this.rateSelect
                let ls8 = this.nameSearch
                let lists = [ls1, ls2, ls3, ls4, ls5, ls6, ls7, ls8]
                lists = lists.reduce(this.mergeTwoLists) //reduce最后变成了单个元素的list而非嵌套的list，不知为什么
                this.hotelListAfterSelect = lists
                this.sortCurrentList()
            },

            /**
             * 以下三个方法是排序相关的
             * 根据不同的checkBox，先记录按照什么排序
             * 然后根据条件综合排序，或者按照一个条件排序，或者默认排序
             * 默认排序即按照hotelId排序
             * */

            sortByStarClicked(e) {
                this.sortByStar = e.target.checked
                this.sortCurrentList()
            },
            sortByCommentClicked(e) {
                this.sortByComment = e.target.checked
                this.sortCurrentList()
            },
            sortCurrentList() {
                let starEnum = ["One", "Two", "Three", "Four", "Five"]
                if (this.sortByStar) {
                    this.hotelListAfterSelect.sort(function (hotel1, hotel2) {
                        return starEnum.indexOf(hotel2.hotelStar) - starEnum.indexOf(hotel1.hotelStar)
                    })
                }
                if (this.sortByComment) {
                    if (!this.sortByStar) {//只按照评分排序
                        this.hotelListAfterSelect.sort(function (hotel1, hotel2) {
                            return hotel2.rate - hotel1.rate
                        })
                    } else {//综合排序
                        this.hotelListAfterSelect.sort(function (hotel1, hotel2) {
                            if (hotel1.hotelStar == hotel2.hotelStar) {
                                return hotel2.rate - hotel1.rate
                            } else {
                                return 0
                            }
                        })
                    }
                }
                if (!this.sortByComment && !this.sortByStar) {//两者都不选,默认按照hotelId排序
                    this.hotelListAfterSelect.sort(function (hotel1, hotel2) {
                        return hotel1.id - hotel2.id
                    })
                }
            },
            /**
             * 这个函数负责根据roomList生成酒店id和房间型号的映射关系
             * 为了筛选的时候筛选到有空房的酒店
             * */
            initRoomMap() {
                let rl = this.roomList
                let roomMap = [];
                for (let i = 0; i < rl.length; i++) {
                    let cur = rl[i]
                    let inMap = false
                    for (let j = 0; j < roomMap.length; j++) {
                        if (roomMap[j].id == cur.hotelId) {
                            roomMap[j].roomType.push(cur.roomType)
                            roomMap[j].prices.push(cur.price)  //价格可能重复，但是没有关系
                            roomMap[j].curNums.push(cur.curNum)
                            inMap = true
                        }
                    }
                    if (!inMap) {
                        roomMap.push({
                            id: cur.hotelId,
                            roomType: [cur.roomType],
                            prices: [cur.price],
                            curNums: [cur.curNum]
                        })
                    }
                }
                this.roomMap = roomMap
            },
            /**
             * 由setHotelListAfterSelect函数调用
             * 求两个hotel的对象数组的交集
             * */
            mergeTwoLists(ls1, ls2) {//用于求两个筛选条件作用下生成的两个list的交集
                let newList = []
                for (let i = 0; i < ls1.length; i++) {
                    let curElem = ls1[i]
                    for (let j = 0; j < ls2.length; j++) {
                        if (curElem.id == ls2[j].id) {
                            newList.push(curElem)
                        }
                    }
                }
                return newList
            }
        }
    }
</script>
<style scoped lang="less">
    .hotelList {
        text-align: center;
        padding: 50px 0;

        .sortCheckBoxes {
            text-align: left;
            margin-left: auto;
        }

        .selectors {
            text-align: left;
            margin-left: auto;

            .selector1 {
                margin-left: auto;
            }

            .selector2 {
                margin-left: auto;
            }
            .searchBox{
                width: 300px;
                margin-left: 40%;
            }
        }

        .emptyBox {
            height: 0;
            margin: 10px 10px
        }

        .card-wrapper {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            flex-grow: 3;
            min-height: 800px
        }

        .card-wrapper .card-item {
            margin: 30px;
            position: relative;
            height: 188px;
        }
    }
</style>