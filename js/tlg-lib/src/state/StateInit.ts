import { State } from './State'
import { PlatformContext } from '../context/PlatformContext'
import { StateID } from './StateID'
import { StorageContext } from '../context/StorageContext'

export class StateInit extends State {
    public getID (): number {
        return StateID.ID_INIT
    }

    public init (c:PlatformContext):StateID {
        console.log('StateInit::Init()')
        if (this.isStorageCompatible(this.storage)) {
            return this.readState(this.storage)
        }
        return this.reset(c)
    }

    private isStorageCompatible (s:StorageContext):boolean {
        const version = s.readNumber()
        return (version === State.STATE_FILE_VERSION)
    }

    private readState (s:StorageContext):StateID {
        this.context.readState(s)
        return new StateID(s.readNumber())
    }

    private reset (gc:PlatformContext):StateID {
        this.context.init()
        return new StateID(StateID.ID_RUNNING).toReset()
    }
}
