import { State } from './State';
import { InternalContext } from '../context/InternalContext';
import { PlatformContext } from '../context/PlatformContext';
import { StateID } from './StateID';
import { StorageContext } from '../context/StorageContext';

export class StatePaused extends State {
    
    constructor(c:InternalContext, s:StorageContext) {
        super(c, s);
        this.context.setStatusText("Paused");
    }


    public getID(): number {
        return StateID.ID_PAUSED;
    }

    
    public togglePause(c:PlatformContext):StateID {
        return new StateID(StateID.ID_RUNNING);
    }
}

