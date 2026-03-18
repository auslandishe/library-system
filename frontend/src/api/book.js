import request from '@/utils/request'

export const listBooksApi = () => request.get('/books')
export const myBorrowListApi = () => request.get('/books/my-borrows')