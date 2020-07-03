import {
    getManagerListAPI,
    addManagerAPI,
    deleteHotelManagerAPI,
    addHotelAPI,
    deleteHotelAPI,
    setHotelManagerAPI,
    addSalesmanAPI,
    getAllSalesmenAPI,
    deleteSalesmanAPI,
    deleteUserAPI,
} from '@/api/admin'


import {message} from 'ant-design-vue'

const admin = {
    state: {
        managerList: [],
        addManagerModalVisible: false,
        addManagerParams: {
            email: '',
            password: '',
        },
        //迭代3.3，addManager也可以添加网站营销人员，通过新增的type区别
        addManagerType: '',
        salesmanList: [],

        //迭代3.3 移植 admin添加酒店功能所需参数
        addHotelModalVisible: false,

        addHotelParams: {
            name: '',
            address: '',
            bizRegion: '',
            hotelStar: '',
//            rate: 0,  评分和评论由后台逻辑计算
//            commentNum: 0,
            description: '',
            phoneNum: '',
            // managerId:'', managerId要等到admin添加工作人员之后再绑定
        },

        normalUserList: [], //3.7，根据salesman的用户名单，直接在store里筛选一般用户了，为了和其他名单统一
    },
    mutations: {
        set_managerList: function (state, data) {
            state.managerList = data
        },
        set_addManagerModalVisible: function (state, data) {
            state.addManagerModalVisible = data
        },
        set_addManagerParams: function (state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        //迭代3.3 移植，添加、删除酒店功能由管理员所有
        set_addHotelModalVisible: function (state, data) {
            state.addHotelModalVisible = data
        },
        set_addHotelParams: function (state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        //迭代3.3新增，关于网站营销人员的部分
        set_addManagerType: function (state, data) {
            state.addManagerType = data
        },
        set_salesmanList: function (state, data) {
            state.salesmanList = data
        },
        set_normalUserList: function (state, data) {
            state.normalUserList = data
        }
    },
    actions: {
        getManagerList: async ({commit}) => {
            const res = await getManagerListAPI()
            if (res) {
                commit('set_managerList', res)
            }
        },
        addManager: async ({state, commit, dispatch}, curHotelId) => {//迭代3.3修改，添加成功后绑定酒店

            if (state.addManagerType == "HotelManager") {
                const res = await addManagerAPI(state.addManagerParams)
                if (res) {
                    commit('set_addManagerParams', {//清空数据
                        email: '',
                        password: ''
                    })
                    commit('set_addManagerModalVisible', false)

                    //绑定manager和酒店
                    const res2 = await setHotelManagerAPI(curHotelId, res)
                    dispatch('getManagerList')
                    dispatch('getHotelList')
                    if (res2) {
                        message.success('添加成功')
                    }
                } else {
                    message.error('添加失败')
                }
            } else if (state.addManagerType == "Salesman") {//迭代3.3修改，根据addManagerType判断添加的是manager还好说salesman
                const res = await addSalesmanAPI(state.addManagerParams)
                if (res) {
                    commit('set_addManagerParams', {//清空数据
                        email: '',
                        password: ''
                    })
                    commit('set_addManagerModalVisible', false)
                    dispatch('getSalesmanList')
                    message.success('添加成功')
                } else {
                    message.error('添加失败')
                }

            }
        },
        //迭代3.1 增，这样命名是为了防止vue文件里deleteHotelManager出错
        deleteHotelManagerA: async ({commit, dispatch}, managerId) => {
            const res = await deleteHotelManagerAPI(managerId)
            if (res) {
                dispatch('getHotelList')
                dispatch('getManagerList')
                message.success('删除用户成功')
            } else {
                message.error('删除用户失败')
            }
        },
        deleteSalesmanA: async ({commit, dispatch}, salesmanId) => {
            const res = await deleteSalesmanAPI(salesmanId)
            if (res) {
                dispatch('getSalesmanList')
                message.success('删除用户成功')
            } else {
                message.error('删除用户失败')
            }
        },
        deleteHotelA: async ({commit, dispatch}, data) => {
            const res = await deleteHotelAPI(data)
            if (res) {
                dispatch('getHotelList')
                message.success("删除酒店成功")
                //把酒店信息从界面上移除

            } else {
                message.error("删除酒店失败")
            }

        },
        // 迭代3.3 移植自hotelManager
        addHotel: async ({state, dispatch, commit}) => {
            const res = await addHotelAPI(state.addHotelParams)
            if (res) {
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion: '',
                    hotelStar: '',
                    rate: 0,
                    description: '',
                    phoneNum: '',
                    // managerId:'',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        //迭代3.3新增
        getSalesmanList: async ({commit}) => {
            const res = await getAllSalesmenAPI()
            if (res) {
                commit('set_salesmanList', res)
            }
        },
        //3.7 删除普通用户
        deleteNormalUser: async ({commit, dispatch}, userId) => {
            const res = await deleteUserAPI(userId)
            if(res){
                message.success("删除成功")
                dispatch('getNormalUserList')
            }
            else{
                message.error("删除失败")
            }
        },
        // 获取普通用户列表，作用机理是从所有用户中过滤
        getNormalUserList: async ({commit, dispatch, rootGetters}) => {//这个方法没有直接调用API
            await dispatch('getUserList')  //加了await用户名单就正常了……
            let userList = rootGetters.userList
            let res = userList.filter(function (item) {
                return item.userType == "Client"
            })
            commit('set_normalUserList', res)
        }
    }
}
export default admin