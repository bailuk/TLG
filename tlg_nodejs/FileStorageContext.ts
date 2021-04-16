import { StorageContext } from './tlg/context/StorageContext';

export class LocalStorageContext extends StorageContext {
    private idR:number = 0;
    private idW:number = 0;
    private storage:Storage;


    constructor() {
        super();
        this.storage = window.localStorage;
    }


    public writeNumber(n: number): void {
        this.storage.setItem(this.idW.toString(), n.toString());
        this.idW++;
    }    
    
    
    public readNumber(): number {
        var result = Number(this.storage.getItem(this.idR.toString()));
        this.idR++;

        return result;
    }
    
    
    public close(): void {
        console.log("R: " + this.idR + " W: " + this.idW);

        this.idR = 0;
        this.idW = 0;
    }


    

    public static factory():StorageContext {
        if (this.storageAvailable()) {
            return new LocalStorageContext();
        }

        return new StorageContext();
    }

    
    private static storageAvailable():boolean {
        var storage:Storage;
        try {
            storage = window.localStorage;
            var x = '__storage_test__';
            storage.setItem(x, x);
            storage.removeItem(x);
            return true;
        }
        catch(e) {
            return e instanceof DOMException && (
                // everything except Firefox
                e.code === 22 ||
                // Firefox
                e.code === 1014 ||
                // test name field too, because code might not be present
                // everything except Firefox
                e.name === 'QuotaExceededError' ||
                // Firefox
                e.name === 'NS_ERROR_DOM_QUOTA_REACHED') &&
                // acknowledge QuotaExceededError only if there's something already stored
                (storage && storage.length !== 0);
        }
    }
}