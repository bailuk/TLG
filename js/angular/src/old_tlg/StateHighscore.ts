import { State } from './State';
import { InternalContext } from './InternalContext';
import { PlatformContext } from './PlatformContext';
import { HighscoreList } from './HighscoreList';
import { StateID } from './StateID';
import { StorageContext } from './StorageContext';

export class StateHighscore extends State {
    
    constructor(c:InternalContext, s:StorageContext) {
        super(c,s);
        this.context.setStatusText("New Highscore!");
    }

    
    public init(c:PlatformContext):StateID {
        // c.onNewHighscore();
        return super.init(c);
    }

    
    
    public setHighscoreName(c:PlatformContext, name:string):StateID {

        if (name.length>0) {
            let highscoreList = new HighscoreList(c);

            highscoreList.add(name, this.context.currentScore.getScore());
            highscoreList.writeState(c);

            return new StateID(StateID.ID_LOCKED);

        } else {
            return super.setHighscoreName(c,name);
        }
    }

    
    public getID() {
        return StateID.ID_HIGHSCORE;
    }
}