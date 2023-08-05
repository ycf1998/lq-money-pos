import request from '@/api/request'

export function count(params) {
  return request({
    url: `/omsOrder/count`,
    method: 'get',
    params
  })
}

export function getDetial(id) {
  return request({
    url: `/omsOrder/detail?id=${id}`,
    method: 'get'
  })
}

export function returnOrder(data) {
  return request({
    url: `/omsOrder/returnOrder`,
    method: 'delete',
    data
  })
}

export function returnGoods(data) {
  return request({
    url: `/omsOrder/returnGoods`,
    method: 'delete',
    data
  })
}

export default { count, getDetial, returnOrder, returnGoods }

