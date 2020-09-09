import {
    addRoomAPI,
    //addHotelAPI,
    //deleteHotelAPI,
    executeOrderAPI,
    finishOrderAPI,
    retrieveHotelByManagerAPI,
    allOrdersAPI,
    updateHotelInfoAPI,
    deleteCooperativeCompanyAPI,
    addCooperativeCompanyAPI,
    updateRoomCurNumAPI,
    deleteServiceAPI,
    addServiceAPI,
    getAllServicesAPI,
} from '@/api/hotelManager'
import {
    getAllOrdersAPI,
    deleteOrderAPI,
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    timeCouponAPI,
    targetRoomCouponAPI,
    birthdayCouponAPI,
    companyCouponAPI,
} from '@/api/coupon'

import {message} from 'ant-design-vue'
import {getHotelByIdAPI} from "../../api/hotel";
import roomList from "../../views/hotel/components/roomList";

const hotelManager = {
    state: {
        orderList: [],
        /*
        addHotelParams: {
            name: '',
            address: '',
            bizRegion:'XiDan',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
            // managerId:'',
        },

         */
        //addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        addCooperativeCompanyVisible: false,
        activeHotelId: 0,
        couponList: [],

        targetHotel: {}, //迭代3.3新增，想不出名字了，存储这个manager所管理的酒店
        targetHotelOrderList: [],//迭代3.3新增 所管理酒店的所有订单
        changeRoomNumModalVisible: false, //更改房间数量的对话框，用于线下预订时房间数量变更

        serviceVisible: false,//酒店服务对话框
        addServiceVisible: false,//增加酒店服务对话框
        allServicesList:[],//所有服务列表
    },
    mutations: {
        set_orderList: function (state, data) {
            state.orderList = data
        },
        /*
        set_addHotelModalVisible: function(state, data) {
            state.addHotelModalVisible = data
        },

         */
        /*
        set_addHotelParams: function(state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },

         */
        set_addRoomModalVisible: function (state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function (state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_couponVisible: function (state, data) {
            state.couponVisible = data
        },
        set_serviceVisible: function (state, data) {
            state.serviceVisible = data
        },
        set_addServiceVisible: function (state, data) {
            state.addServiceVisible = data
        },

        set_activeHotelId: function (state, data) {
            state.activeHotelId = data
        },
        set_couponList: function (state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function (state, data) {
            state.addCouponVisible = data
        },
        set_targetHotel: function (state, data) {
            state.targetHotel = {
                ...state.targetHotel,
                ...data,
            }
        },
        set_targetHotelOrderList: function (state, data) {
            state.targetHotelOrderList = data
        },
        set_addCooperativeCompanyVisible: function (state, data) {
            state.addCooperativeCompanyVisible = data
        },
        set_changeRoomNumModalVisible: function (state, data) {
            state.changeRoomNumModalVisible = data
        },
        set_allServicesList: function (state, data) {
            state.allServicesList = data
        },
    },
    actions: {
        getAllOrders: async ({state, commit}) => {
            const res = await getAllOrdersAPI()
            if (res) {
                commit('set_orderList', res)
            }
        },
        getAllServices: async ({state,commit})=>{
            const res = await getAllServicesAPI()
            if(res){
                commit('set_allServicesList',res)
            }
        },
        /*迭代3.3 去掉addHotel，搬运到admin
        addHotel: async({ state, dispatch, commit }) => {
            const res = await addHotelAPI(state.addHotelParams)
            if(res){
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion:'XiDan',
                    hotelStar:'',
                    rate: 0,
                    description:'',
                    phoneNum:'',
                    // managerId:'',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
         */
        addRoom: async ({state, dispatch, commit, rootGetters}) => {
            const res = await addRoomAPI(state.addRoomParams)
            if (res) {
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
                dispatch('getTargetHotel',rootGetters.userId)
            } else {
                message.error('添加失败')
            }
        },
        getHotelCoupon: async ({state, commit}) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if (res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList', res)
            }
        },

        /**
         * 添加优惠券
         * 根据不同的优惠券种类调用不同api
         * */
        addHotelCoupon: async ({commit, dispatch}, data) => {
            let res = null;

            if (data.type == 1) {
                res = await birthdayCouponAPI(data)
            }
            if (data.type == 2) {
                res = await targetRoomCouponAPI(data)
            } else if (data.type == 3) {
                res = await hotelTargetMoneyAPI(data)
            } else if (data.type == 4) {
                res = await timeCouponAPI(data)
            } else if (data.type == 5) {
                res = await companyCouponAPI(data)
            }
            if (res) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                if (data.hotelId == -1) {
                    dispatch('getGlobalHotelCouponList')
                }
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', true)
                message.success('添加策略成功')
            } else {
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        /* 迭代3.3 去掉这个方法，搬运到admin
        deleteHotelM: async ({commit, dispatch}, data) => {
            console.log("调用成功")
            const res = await deleteHotelAPI(data)
            if (res) {
                dispatch('getHotelList')
                message.success("删除酒店成功")
                //把酒店信息从界面上移除

            } else {
                message.error("删除酒店失败")
            }

        },

         */
        deleteOrderM: async ({commit, dispatch}, orderId) => {
            const res = await deleteOrderAPI(orderId)
            if (res) {
                //dispatch('getAllOrders')
                dispatch('getTargetHotelOrderList')
                message.success('删除订单成功')
            } else {
                message.error('删除订单失败')
            }
        },

        //迭代3.2
        changeOrderState: async ({commit, dispatch, rootGetters}, data) => {
            if (data.newOrderState == '已执行') {
                //console.log("store层:已执行")
                //console.log(data.id)

                const res = await executeOrderAPI(data.id)
                if (res) {
                    //dispatch('getAllOrders')
                    dispatch('getTargetHotelOrderList')
                    commit('set_showOrderDetailModalVisible', false) //这个调用了store/modules/user里面的方法，把表单隐藏
                    message.success("更改为已执行")
                } else {
                    message.error("更改失败")
                }

            } else if (data.newOrderState == '已退房') {
                //console.log("store层:已退房")
                //console.log(data.id)

                const res = await finishOrderAPI(data.id)
                if (res) {
                    //dispatch('getAllOrders')
                    dispatch('getTargetHotel',rootGetters.userId)
                    dispatch('getTargetHotelOrderList')
                    commit('set_showOrderDetailModalVisible', false) //这个调用了store/modules/user里面的方法，把表单隐藏
                    message.success("更改为已退房")
                } else {
                    message.error("更改失败")
                }
            }

        },
        //迭代3.3
        getTargetHotel: async ({commit, dispatch, state}, managerId) => {
            const res = await retrieveHotelByManagerAPI(managerId)
            if (res) {
                commit('set_targetHotel', res)
            }
        },
        getTargetHotelOrderList: async ({commit, state}) => {
            const res = await allOrdersAPI(state.targetHotel.id)
            if (res) {
                commit('set_targetHotelOrderList', res)
            }
        },
        updateHotelInfo: async ({state, dispatch, rootGetters}, data) => {
            const params = {
                id: state.targetHotel.id,
                ...data,
            }
            const res = await updateHotelInfoAPI(params)
            if (res) {
                message.success('修改成功')
                console.log(rootGetters.userId)
                dispatch('getTargetHotel', rootGetters.userId) // state.targetHotel.managerId
            }
        },
        deleteCooperativeCompany: async ({commit, dispatch, rootGetters}, data) => {
            const res = await deleteCooperativeCompanyAPI(data)
            if (res) {
                dispatch('getTargetHotel', rootGetters.userId)
                message.success('删除合作企业成功')
            } else {
                message.error('删除合作企业失败')
            }
        },

        addCooperativeCompany: async ({commit, dispatch, rootGetters}, data) => {
            const params = {
                hotelId: rootGetters.targetHotel.id,
                ...data,
            }
            const res = await addCooperativeCompanyAPI(params)
            if (res) {
                commit('set_addCooperativeCompanyVisible', false)
                dispatch('getTargetHotel', rootGetters.userId)
                message.success('添加合作企业成功')
            } else {
                message.error('添加合作企业失败')
            }
        },
        updateRoomNum: async ({commit, dispatch,rootGetters}, data) => {
            const res = await updateRoomCurNumAPI(data.hotelId, data.roomType, data.num)
            if(res){
                message.success("修改房间数量成功")
                dispatch('getTargetHotel',rootGetters.userId)
            }
            else{
                message.error("修改房间数量失败")
            }
        },
        deleteHotelService: async ({commit, dispatch, rootGetters}, data) => {
            const res = await deleteServiceAPI(data.hotelId,data.serviceType)
            if (res) {
                dispatch('getTargetHotel', rootGetters.userId)
                message.success('删除服务成功')
            } else {
                message.error('删除服务失败')
            }
        },
        addHotelService: async ({commit, dispatch, rootGetters},data) => {
            const res = await addServiceAPI(data.hotelId,data.serviceType)
            if (res) {
                commit('set_addServiceVisible', false)
                commit('set_serviceVisible',true)
                dispatch('getTargetHotel', rootGetters.userId)
                message.success('添加服务成功')
            } else {
                message.error('添加服务失败')
            }
        },
    }
}
export default hotelManager