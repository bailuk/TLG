import { InternalContext } from './InternalContext';
import { PlatformContext } from './PlatformContext';
import { StateID } from './StateID';
import { StorageContext } from './StorageContext';

export abstract class State {
        public static readonly STATE_FILE_VERSION=1;

        protected readonly context:InternalContext;
        protected readonly storage:StorageContext;


        constructor(c:InternalContext, s:StorageContext) {
                this.context=c;
                this.storage = s;
        }


        public init(c:PlatformContext):StateID {
                return StateID.IGNORE;
        }


        public resume(c:PlatformContext):StateID {
                return StateID.IGNORE;
        }


        public moveLeft(c:PlatformContext):StateID {
                return StateID.IGNORE;
        }


        public moveRight(c:PlatformContext):StateID {
                return StateID.IGNORE;
        }

        public moveDown(c:PlatformContext):StateID {
                return StateID.IGNORE;
        }

        public moveTurn(c:PlatformContext):StateID {
                return StateID.IGNORE;
        }


        public startNewGame(c:PlatformContext):StateID {
                return new StateID(StateID.ID_RUNNING).toReset();
        }



        public togglePause(c:PlatformContext):StateID {
                return StateID.IGNORE;
        }


        public getTimerInterval():number {
                return 500;
        }


        public abstract getID():number;

        public setHighscoreName(c:PlatformContext, name:string):StateID {
                return StateID.IGNORE;
        }


        public writeState(): void {
                this.storage.writeNumber(State.STATE_FILE_VERSION);
                this.context.writeState(this.storage);
        this.storage.writeNumber(this.getID());
    }
}
