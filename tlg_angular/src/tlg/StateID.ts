export class StateID {
    public static readonly ID_IGNORE=0;
    public static readonly ID_HIGHSCORE=1;
    public static readonly ID_INIT=2;
    public static readonly ID_LOCKED=3;
    public static readonly ID_PAUSED=4;
    public static readonly ID_RUNNING=5;


    
    private _id:number;
    private _reset:boolean;

    public static readonly IGNORE = new StateID(StateID.ID_IGNORE);

    constructor(id: number) {
        this._id = id;
        this._reset = false;
    }

    public toReset() : StateID {
        var result =  new StateID(this.id());
        result._reset=true;
        return result;
    }

    public resume():boolean {
        return !this._reset;
    }

    public reset():boolean {
        return this._reset;
    }

    public id():number {
        return this._id;
    }


}