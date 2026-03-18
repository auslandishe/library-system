import request from '@/utils/request'

export const borrowApi = (data) => request.post('/borrow', data)
export const returnApi = (data) => request.post('/borrow/return', data)