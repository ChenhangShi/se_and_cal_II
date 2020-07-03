import {message} from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    getHotelCommentsAPI,
    getAllBizRegionsAPI,
    getAllRoomsAPI,
} from '@/api/hotel'
import {
    reserveHotelAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'

const hotel = {
    state: {
        hotelList: [],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {},
        orderModalVisible: false,
        currentOrderRoom: {},
        orderMatchCouponList: [],
        //3.4添加所有商圈的属性是因为要在hotelList页面按照商圈筛选酒店
        hotelComments: [],//当前酒店评论
        bizRegionList: [],//所有商圈信息，用于新建酒店时的下拉框
        roomListModalVisible: false, //迭代3.6增，在主界面预订酒店展示该酒店的roomList
        roomList: [] //迭代3.6增，为了根据房间信息检索酒店
    },
    mutations: {
        set_hotelList: function (state, data) {
            state.hotelList = data
        },
        set_hotelListParams: function (state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function (state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function (state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function (state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function (state, data) {
            state.orderModalVisible = data
        },
        set_currentOrderRoom: function (state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function (state, data) {
            state.orderMatchCouponList = data
        },
        set_hotelComments: function (state, data) {
            state.hotelComments = data
        },
        set_bizRegionList: function (state, data) {
            state.bizRegionList = data
        },
        set_roomListModalVisible: function (state, data) {
            state.roomListModalVisible = data
        },
        set_roomList: function (state, data) {
            state.roomList = data
        }
    },

    actions: {
        getHotelList: async ({commit, state}) => {
            const res = await getHotelsAPI()
            if (res) {
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelById: async ({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if (res) {
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async ({state, commit, dispatch}, data) => {
            const res = await reserveHotelAPI(data)
            console.log(res)
            if (res) {
                message.success('预定成功')
                commit('set_orderModalVisible', false)
                dispatch('getUserOrders')
            }
        },
        getOrderMatchCoupons: async ({state, commit}, data) => {
            const res = await orderMatchCouponsAPI(data)
            if (res) {
                commit('set_orderMatchCouponList', res)
            }
        },
        getHotelComments: async ({state, commit}) => {
            const res = await getHotelCommentsAPI(Number(state.currentHotelId))
            if (res) {
                commit('set_hotelComments', res)
            }
        },
        getAllBizRegions: async ({state, commit}) => {
            const res = await getAllBizRegionsAPI()
            if (res) {
                commit('set_bizRegionList', res)
            }
        },
        getRoomList: async ({state, commit}) => {
            const res = await getAllRoomsAPI()
            if (res) {
                commit('set_roomList', res)
            }
        },
    }
}

export default hotel