import {axios} from '@/utils/request'

const api = {
    adminPre: '/api/admin',
    hotelPre: '/api/hotel',
}

export function getManagerListAPI() {
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'GET'
    })
}

export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}

export function deleteHotelManagerAPI(managerId) {
    return axios({
        url: `${api.adminPre}/${managerId}/deleteManager`,
        method: 'POST',
    })

}

export function deleteSalesmanAPI(salesmanId) {
    return axios({
        url: `${api.adminPre}/${salesmanId}/deleteSalesman`,
        method: 'POST',
    })

}

//迭代3.3移植自hotelManager
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}

export function deleteHotelAPI(hotelId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/deleteHotel`,
        method: `POST`,
    })
}

//迭代3.3新增
export function setHotelManagerAPI(hotelId, managerId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/setHotelManager`,
        method: `POST`,
        params:{hotelId,managerId}
    })
}

export function addSalesmanAPI(data) {
    return axios({
        url: `${api.adminPre}/addSalesman`,
        method: `POST`,
        data,
    })
}

export function getAllSalesmenAPI() {
    return axios({
        url: `${api.adminPre}/getAllSalesmen`,
        method: `GET`,
    })
}

//3.7
export function deleteUserAPI(userId) {
    return axios({
        url: `${api.adminPre}/${userId}/deleteUser`,
        method: 'POST',
    })
}