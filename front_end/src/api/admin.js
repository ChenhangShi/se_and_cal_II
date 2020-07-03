import {axios} from '@/utils/request'

const api = {
    adminPre: '/api/admin',
    hotelPre: '/api/hotel',
}
// 返回所有酒店工作人员
export function getManagerListAPI() {
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'GET'
    })
}
// 添加酒店工作人员
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
// 删除酒店工作人员
export function deleteHotelManagerAPI(managerId) {
    return axios({
        url: `${api.adminPre}/${managerId}/deleteManager`,
        method: 'POST',
    })

}
// 删除网站营销人员
export function deleteSalesmanAPI(salesmanId) {
    return axios({
        url: `${api.adminPre}/${salesmanId}/deleteSalesman`,
        method: 'POST',
    })

}

//迭代3.3移植自hotelManager，添加酒店
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
//删除酒店
export function deleteHotelAPI(hotelId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/deleteHotel`,
        method: `POST`,
    })
}

//迭代3.3新增 为酒店绑定工作人员
export function setHotelManagerAPI(hotelId, managerId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/setHotelManager`,
        method: `POST`,
        params:{hotelId,managerId}
    })
}
// 添加网站营销人员
export function addSalesmanAPI(data) {
    return axios({
        url: `${api.adminPre}/addSalesman`,
        method: `POST`,
        data,
    })
}
// 获取所有网站营销人员
export function getAllSalesmenAPI() {
    return axios({
        url: `${api.adminPre}/getAllSalesmen`,
        method: `GET`,
    })
}

//3.7 删除普通用户
export function deleteUserAPI(userId) {
    return axios({
        url: `${api.adminPre}/${userId}/deleteUser`,
        method: 'POST',
    })
}