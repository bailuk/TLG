import { InternalContext } from '../context/InternalContext'
import { PlatformContext } from '../context/PlatformContext'
import { StateInit } from './StateInit'
import { StateHighscore } from './StateHighscore'
import { StateLocked } from './StateLocked'
import { StatePaused } from './StatePaused'
import { StateRunning } from './StateRunning'
import { StateID } from './StateID'
import { State } from './State'
import { StorageContext } from '../context/StorageContext'

/**
 * State machine for game logic.
 * Switches between these states:
 *  @class StateInit
 *  @class StateRunning
 *  @class StatePaused
 *  @class StateLocked
 *
 *  @class StateContext
 */
export class StateContext {
    private readonly iContext:InternalContext
    private readonly pContext:PlatformContext
    private readonly sContext:StorageContext

    private tlg.state: State
    private stateID = StateID.ID_IGNORE

    constructor (ic:InternalContext, pc:PlatformContext, sc: StorageContext) {
        this.iContext = ic
        this.pContext = pc
        this.sContext = sc
        this.tlg.state = new StateInit(ic, sc)
        this.set(new StateID(StateID.ID_INIT).toReset())
    }

    /**
     * change tlg.state as described in id
     * @param {@class StateID} id - information on how to enter new tlg.state
     */
    private set (id:StateID):void {
        if (id.id() !== StateID.ID_IGNORE) {
            this.stateID = id.id()
            this.tlg.state = this.factory(id.id())

            if (id.resume()) {
                // enter tlg.state by resume
                console.log('StateContext::resume()')
                this.tlg.state.resume(this.pContext)
            } else {
                // enter tlg.state by reset (init)
                console.log('StateContext::init()')
                this.set(this.tlg.state.init(this.pContext))
            }
        }
    }

    private factory (id:number):State {
        switch (id) {
        case StateID.ID_HIGHSCORE:
            return new StateHighscore(this.iContext, this.sContext)
        case StateID.ID_INIT:
            return new StateInit(this.iContext, this.sContext)
        case StateID.ID_LOCKED:
            return new StateLocked(this.iContext, this.sContext)
        case StateID.ID_PAUSED:
            return new StatePaused(this.iContext, this.sContext)
        }

        return new StateRunning(this.iContext, this.sContext)
    }

    public moveLeft (c:PlatformContext):void {
        this.set(this.tlg.state.moveLeft(c))
    }

    public moveRight (c:PlatformContext):void {
        this.set(this.tlg.state.moveRight(c))
    }

    public moveDown (c:PlatformContext):void {
        this.set(this.tlg.state.moveDown(c))
    }

    public moveTurn (c:PlatformContext):void {
        this.set(this.tlg.state.moveTurn(c))
    }

    public togglePause (c:PlatformContext):void {
        this.set(this.tlg.state.togglePause(c))
    }

    public startNewGame (c:PlatformContext):void {
        this.set(this.tlg.state.startNewGame(c))
    }

    public getTimerInterval ():number {
        return this.tlg.state.getTimerInterval()
    }

    public setHighscoreName (c:PlatformContext, name:string) {
        this.set(this.tlg.state.setHighscoreName(c, name))
    }

    public getID ():number {
        return this.stateID
    }

    public readState () {
        this.sContext.close()
        this.set(new StateID(StateID.ID_INIT).toReset())
    }

    public writeState () {
        this.tlg.state.writeState()
        this.sContext.close()
    }
}
