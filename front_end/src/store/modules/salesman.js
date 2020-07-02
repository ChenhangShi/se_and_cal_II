import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    timeCouponAPI,
    targetRoomCouponAPI,
} from '@/api/coupon'
import {
    getAllUsersAPI,  //归类有点乱...  所以所有用户的名单到底归salesman还是admin
    addCreditAPI,
    getUnusualOrdersAPI,
    annualUnusualOrderAPI,
} from "@/api/salesman";

import {
    changeLevelInfoAPI
} from "@/api/member";

import {message} from 'ant-design-vue'

const salesman = {
    state: {
        globalCouponList: [],
        //couponVisible和addCouponVisible暂时借用hotelManager的
        unusualOrderList: [],
        userList: [],
        selectRestoreMethodModalVisible: false,
        addCreditModalVisible: false,
        changeLevelRulesModalVisible: false,
    },
    mutations: {
        set_globalCouponList: function (state, data) {
            state.globalCouponList = data
        },
        set_unusualOrderList: function (state, data) {
            state.unusualOrderList = data
        },
        set_userList: function (state, data) {
            state.userList = data
        },
        set_selectRestoreMethodModalVisible: function (state, data) {
            state.selectRestoreMethodModalVisible = data
        },
        set_addCreditModalVisible: function (state, data) {
            state.addCreditModalVisible = data
        },
        set_changeLevelRulesModalVisible: function (state, data) {
            state.changeLevelRulesModalVisible = data
        }
    },
    actions: {
        getGlobalHotelCouponList: async ({commit, dispatch}) => {
            const res = await hotelAllCouponsAPI(-1)
            if (res) {
                commit('set_globalCouponList', res)
            }
        },
        //3.5
        getUnusualOrderList: async ({commit}) => {
            const res = await getUnusualOrdersAPI()
            console.log("异常订单列表")
            console.log(res)
            if (res) {
                commit('set_unusualOrderList', res)
            }
        },
        getUserList: async ({commit, state}) => {
            const res = await getAllUsersAPI()
            if (res) {
                commit('set_userList', res)
            }
        },
        annualUnusualOrder: async ({commit, dispatch}, data) => {
            const res = await annualUnusualOrderAPI(data.orderId, data.restoreMethod)
            if (res) {
                dispatch('getUnusualOrderList')
                dispatch('getUserList')
                message.success("撤销成功")
            } else {
                message.error("撤销失败")
            }
        },
        addCredit: async ({commit, dispatch}, data) => {
            const res = await addCreditAPI(data.userId, data.amount)
            if (res) {
                dispatch('getUserList')
                message.success("充值成功")
            } else {
                message.error("充值失败")
            }
        },
        //3.7
        updateMemberLevelInfo: async ({commit, dispatch}, data) => {
            const res=  await changeLevelInfoAPI(data)
            if(res){
                dispatch('getMemberLevelInfo')
                message.success("修改成功")
            }
            else{
                message.error("修改失败")
            }
        }

    }

}
export default salesman