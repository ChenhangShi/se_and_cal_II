import Vue from 'vue'
import router from '@/router'
import {getToken, setToken, removeToken} from '@/utils/auth'
import {resetRouter} from '@/router'
import {message} from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    postCommentAPI,
    getMemberInfoAPI,
    registerMemberAPI,
    getUserCreditRecordsAPI,
} from '@/api/user'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
    retrieveOrderAPI,
} from '@/api/order'
import {
    getLevelInfoAPI,
} from "@/api/member";

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {},
        userOrderList: [],

        //迭代3.2新增，订单详情页面是否展示
        showOrderDetailModalVisible: false,
        //迭代3.2新增，user当前订单属性，到时候查看订单详情的时候暂时不用props传输数据 以及迭代3.5用于撤销异常订单修改信用值
        currentOrder: {},
        //3.4 用户评论对话框
        addCommentModalVisible: false,
        //3.4.5会员相关
        memberInfo: null,  //当前用户的会员信息，null表示非会员
        registerMemberModalVisible: false, //注册会员对话框
        //3.6 信用记录
        userCreditRecord: [],
        //3.7 会员等级表
        memberLevelInfo: [],
    }
}

const user = {
    state: getDefaultState(),
    //showOrderDetailModalVisible: false,
    mutations: {
        reset_state: function (state) {
            state.token = '',
                state.userId = '',
                state.userInfo = {},
                state.userOrderList = []
        },
        set_token: function (state, token) {
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_showOrderDetailModalVisible: function (state, data) {
            state.showOrderDetailModalVisible = data
        },
        set_currentOrder: function (state, data) {
            state.currentOrder = {
                ...state.currentOrder,
                ...data
            }
        },
        set_addCommentModalVisible: function (state, data) {
            state.addCommentModalVisible = data
        },
        set_memberInfo: function (state, data) {
            if (data != null)
                state.memberInfo = data
            else
                state.memberInfo = null
        },
        set_registerMemberModalVisible: function (state, data) {
            state.registerMemberModalVisible = data
        },
        set_userCreditRecord: function (state, data) {
            for(let i=0;i<data.length;i++){
                data[i].change.toFixed(2)
                data[i].result.toFixed(2)
            }
            state.userCreditRecord = data
        },
        set_memberLevelInfo: function (state, data) {
            state.memberLevelInfo = data
        }


    },

    actions: {
        login: async ({dispatch, commit}, userData) => {
            const res = await loginAPI(userData)
            if (res) {
                //迭代3.3 bug fix
                // console.log("登陆信息：")
                // console.log(res)
                sessionStorage.setItem("userId", res.id) //在本地缓存中储存当前用户信息
                //sessionStorage.setItem("")

                setToken(res.id)
                commit('set_userId', res.id)
                dispatch('getUserInfo')
                router.push('/hotel/hotelList')
            }
        },
        register: async ({commit}, data) => {
            const res = await registerAPI(data)
            if (res) {
                message.success('注册成功')
            }
        },
        getUserInfo({state, commit}) {
            return new Promise((resolve, reject) => {
                getUserInfoAPI(state.userId).then(response => {
                    const data = response
                    if (!data) {
                        reject('登录已过期，请重新登录')
                    }
                    //console.log(state.userId)
                    //console.log('刚获取的用户信息：')
                    //console.log(data)

                    commit('set_userInfo', data)
                    //console.log(state.userInfo)
                    commit('set_userId', data.id)
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        updateUserInfo: async ({state, dispatch}, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if (res) {
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        getUserOrders: async ({state, commit}) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserOrdersAPI(data)
            if (res) {
                commit('set_userOrderList', res)
                //console.log(state.userOrderList)
            }
        },
        cancelOrder: async ({state, dispatch}, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if (res) {
                dispatch('getUserOrders')
                dispatch('getUserInfo') //撤销订单可能导致信用变化
                message.success('撤销成功')
            } else {
                message.error('撤销失败')
            }
        },
        logout: async ({commit}) => {
            removeToken()
            resetRouter()
            commit('reset_state')
            //logout清除本地缓存
            sessionStorage.setItem("userId", null)
        },
        // remove token
        resetToken({commit}) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
        //迭代3.2新增 通过后端由orderId来获取order详情，并更新currentOrder
        updateCurrentOrder: async ({commit}, orderId) => {
            const res = await retrieveOrderAPI(orderId)
            if (res) {
                //console.log("从后端获取到到订单:")
                //console.log(res)
                commit('set_currentOrder', res)
            }
        },
        //3.4 发表评论
        postComment: async ({commit, dispatch}, data) => {
            const res = await postCommentAPI(data)
            if (res) {
                commit('set_addCommentModalVisible', false)
                message.success('评论成功')
            } else {
                message.error('评论失败')
            }
        },
        //3.4.5 用户登录时获取会员信息
        getMemberInfo: async ({state, commit}) => {
            const res = await getMemberInfoAPI(state.userId)
            if (res) {
                commit('set_memberInfo', res)
                //console.log("用户会员信息：")
                //console.log(state.memberInfo)
            }
        },
        registerMember: async ({commit, dispatch}, data) => {
            const res = await registerMemberAPI(data)
            if (res) {
                dispatch('getMemberInfo')
                message.success('注册成功')
            } else {
                message.error('注册失败')
            }
        },
        getUserCreditRecord: async ({state, commit}) =>{
            const res = await getUserCreditRecordsAPI(Number(state.userId))
            if(res){
                commit('set_userCreditRecord', res)
                // console.log("信用记录：")
                // console.log(state.userCreditRecord)
            }
        },
        //3.7 获取等级表
        getMemberLevelInfo: async ({state, commit}) => {
            const res = await getLevelInfoAPI()
            if(res){
                commit('set_memberLevelInfo', res)
            }
        },

    },

}

export default user