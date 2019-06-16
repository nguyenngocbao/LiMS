export class Book {
    id: number
    name: string
    isbn: string
    author: string
    description: string
    publisher: string
    category: Category
    quantity: number
    image: string
}

export class Category {
    id: number
    name: string
}