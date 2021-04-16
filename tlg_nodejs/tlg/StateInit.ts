import { State } from './State';
import { InternalContext } from './InternalContext';
import { PlatformContext } from './PlatformContext';
import { StateID } from './StateID';
import { StorageContext } from './StorageContext';

export class StateInit extends State {
    
    constructor(c: InternalContext, s: StorageContext) {
        super(c, s);
    }


    public getID(): number {
        return StateID.ID_INIT;
    }

    
    public init(c:PlatformContext):StateID {
        console.log('StateInit::Init()');
        if (this.isStorageCompatible(this.storage)) {
            return this.readState(this.storage);
        }
        return this.reset(c);
    }


    private isStorageCompatible(s:StorageContext):boolean {
        var version = s.readNumber();
        return (version == State.STATE_FILE_VERSION);
    }


    private readState(s:StorageContext):StateID {
        this.context.readState(s);
        return new StateID(s.readNumber());
    }


    private reset(gc:PlatformContext):StateID {
        this.context.init();
        return new StateID(StateID.ID_RUNNING).toReset();
    }
}
