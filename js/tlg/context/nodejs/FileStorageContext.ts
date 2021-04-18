declare var require: any

const fs = require('fs')

import { StorageContext } from '../StorageContext'


export class FileStorageContext extends StorageContext {
    private idR:number = 0
    private idW:number = 0
    private data:number[] = []

    constructor() {
        super()
        console.log('FileStorageContext::init()')

        try {
            this.data = JSON.parse(fs.readFileSync('config', 'utf8'))
        } catch (err) {
            console.error(err)
        }
    }


    public writeNumber(n: number): void {
        if (this.idW < this.data.length) {
            this.data[this.idW] = n
        } else {
            this.data.push(n)
        }

        this.idW++
    }    
    
    
    public readNumber(): number {
        let result = 0
 
        if (this.idR < this.data.length) {
            result = this.data[this.idR]
        }

        this.idR++
        return result
    }
    
    
    public close(): void {
        console.log('FileStorageContext::close()')

        this.idR = 0
        this.idW = 0
        
        try {
            fs.writeFileSync('config', JSON.stringify(this.data), 'utf8')
        } catch (err) {
            console.error(err)
        }
    }
}