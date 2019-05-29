import { Injectable } from '@angular/core';

@Injectable()
export class ViewService {
    BOOKS = [
        { id: 1, name: 'Sự kết thúc của thời đại giả kim', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/49/70/ff/145b8f5b9bd04c6f19262680f5d58bc5.jpg', quantity: 5, type: 'Truyện', author: 'Mervyn King', available: true },
        { id: 2, name: 'Thế giới ba không', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/72/5a/3a/0574296cfae195f71ca9e964ef56abe9.jpg', quantity: 5, type: 'Truyện', author: 'Muhammad Yunus', available: true },
        { id: 3, name: 'Anh Đã Quên Em Chưa?', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/87/b8/5f/bc45e30fd21ebb1c1cafade52766e069.jpg', quantity: 5, type: 'Truyện', author: 'Tuệ Mẫn', available: false },
        { id: 4, name: 'Hành Trình Về Phương Đông', image: 'https://salt.tikicdn.com/cache/200x200/media/catalog/product/h/a/hanh_trinh_ve_phuong_dong_2.jpg', quantity: 5, type: 'Truyện', author: 'Baird T. Spalding', available: true },
        { id: 5, name: 'Sống Thực Tế Giữa Đời Thực Dụng', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/25/d6/2c/f88080bba78a779fb78e1b76b73a9813.jpg', quantity: 5, type: 'Truyện', author: 'Mễ Mông', available: true }
        , { id: 5, name: 'All The Rule - Sống Bản Lĩnh Theo Cách Một Quý Cô', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/78/07/33/0f93c8df023372177fab695d0ea531ca.png', quantity: 5, type: 'Truyện', author: 'Ellen Fein, Sherrie Schneider', available: true }
    ]
    LOANBOOKS = [
        { id: 1, bookName: 'Mắt biếc', UserName: 'nngocbao', requestDate: '25/5/2019' },
        { id: 2, bookName: 'Đảo mộng mơ', UserName: 'nvhoai', requestDate: '25/5/2019' },
        { id: 3, bookName: 'Con chó và con mèo', UserName: 'lthang', requestDate: '25/5/2019' },
        { id: 4, bookName: 'Con cá', UserName: 'ttthuthuy', requestDate: '25/5/2019' },
        { id: 5, bookName: 'Con cá trèo lên cây', UserName: 'ncanh', requestDate: '25/5/2019' }
    ]
    REQUEST = [
        { id: 1,requestDate:'25/5/2019', name: 'Sự kết thúc của thời đại giả kim', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/49/70/ff/145b8f5b9bd04c6f19262680f5d58bc5.jpg', quantity: 5, type: 'Truyện', author: 'Mervyn King', available: true },
        { id: 2,requestDate:'25/5/2019', name: 'Thế giới ba không', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/72/5a/3a/0574296cfae195f71ca9e964ef56abe9.jpg', quantity: 5, type: 'Truyện', author: 'Muhammad Yunus', available: true },
        { id: 3,requestDate:'25/5/2019', name: 'Anh Đã Quên Em Chưa?', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/87/b8/5f/bc45e30fd21ebb1c1cafade52766e069.jpg', quantity: 5, type: 'Truyện', author: 'Tuệ Mẫn', available: false },

    ]
    LOANING = [
        { id: 1,requestDate:'25/5/2019', name: 'Sự kết thúc của thời đại giả kim', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/49/70/ff/145b8f5b9bd04c6f19262680f5d58bc5.jpg', quantity: 5, type: 'Truyện', author: 'Mervyn King', available: true },
        { id: 2,requestDate:'25/5/2019', name: 'Thế giới ba không', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/72/5a/3a/0574296cfae195f71ca9e964ef56abe9.jpg', quantity: 5, type: 'Truyện', author: 'Muhammad Yunus', available: true },
        { id: 3,requestDate:'25/5/2019', name: 'Anh Đã Quên Em Chưa?', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/87/b8/5f/bc45e30fd21ebb1c1cafade52766e069.jpg', quantity: 5, type: 'Truyện', author: 'Tuệ Mẫn', available: false },

    ]
    RESERVE = [
        { id: 1,requestDate:'25/5/2019', name: 'Sự kết thúc của thời đại giả kim', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/49/70/ff/145b8f5b9bd04c6f19262680f5d58bc5.jpg', quantity: 5, type: 'Truyện', author: 'Mervyn King', available: true },
        { id: 2,requestDate:'25/5/2019', name: 'Thế giới ba không', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/72/5a/3a/0574296cfae195f71ca9e964ef56abe9.jpg', quantity: 5, type: 'Truyện', author: 'Muhammad Yunus', available: true },
        { id: 3,requestDate:'25/5/2019', name: 'Anh Đã Quên Em Chưa?', image: 'https://salt.tikicdn.com/cache/200x200/ts/product/87/b8/5f/bc45e30fd21ebb1c1cafade52766e069.jpg', quantity: 5, type: 'Truyện', author: 'Tuệ Mẫn', available: false },

    ]

    getBook() {
        return this.BOOKS
    }
    getLoanBook() {
        return this.LOANBOOKS
    }
}