import {axios} from '@/utils/request'

const api = {
    couponPre: '/api/coupon'
}

export function hotelTargetMoneyAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelTargetMoney`,
        method: 'POST',
        data,
    })
}

export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/hotelAllCoupons`,
        method: 'GET',
        params: {hotelId: hotelId},
    })
}

export function orderMatchCouponsAPI(params) {
    return axios({
        url: `${api.couponPre}/orderMatchCoupons`,
        method: 'GET',
        params,
    })
}

//迭代3.3新增
export function timeCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/timeCoupon`,
        method: `POST`,
        data,
    })
}

export function targetRoomCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/targetRoomCoupon`,
        method: `POST`,
        data,
    })
}

//3.4.5
export function birthdayCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/birthdayCoupon`,
        method: 'POST',
        data,
    })
}

export function companyCouponAPI(data) {
    return axios({
        url: `${api.couponPre}/companyCoupon`,
        method: 'POST',
        data,
    })
}