import { State } from './State'
import { InternalContext } from '../context/InternalContext'
import { StorageContext } from '../context/StorageContext'
import { StateID } from './StateID'

export class StateLocked extends State {
    constructor (c:InternalContext, s:StorageContext) {
        super(c, s)
        this.context.setStatusText('Game over')
    }

    public getID (): number {
        return StateID.ID_LOCKED
    }
}
