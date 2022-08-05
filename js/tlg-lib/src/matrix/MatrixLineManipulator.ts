import { MatrixWithShape } from './MatrixWithShape'

export class MatrixLineManipulator extends MatrixWithShape {
    private greyedLines:number = 0

    public eraseLines () {
        let count = 0

        for (let y = 0; y < this.getHeight(); y++) {
            if (this.canLineBeRemoved(y)) {
                this.removeLine(y)
                count++
            }
        }
        return count
    }

    public insertGreyedLine () {
        const y = this.getHeight() - this.greyedLines - 1

        if (y > 0) {
            for (let x = 0; x < this.getWidth(); x++) this.getD(x, y).enableGreyedOut()
            this.greyedLines++
        }
    }

    public removeGreyedLine () {
        const y = this.getHeight() - this.greyedLines

        if (y < this.getHeight() && y > 0) {
            for (let x = 0; x < this.getWidth(); x++) this.getD(x, y).disableGreyedOut()
            this.greyedLines--
        }
    }

    private canLineBeRemoved (y:number) {
        let r = true
        for (let x = 0; r && x < this.getWidth(); x++) { r = this.getXY(x, y).isActivated() }

        return r
    }

    private removeLine (l:number) {
        for (let y = l; y > 0; y--) {
            this.moveLineOneDown(y)
        }
        this.eraseTopLine()
    }

    private moveLineOneDown (l:number) {
        for (let x = 0; x < this.getWidth(); x++) this.getD(x, l).set(this.getXY(x, l - 1))
    }

    private eraseTopLine () {
        for (let x = 0; x < this.getWidth(); x++) this.getD(x, 0).makeInvisible()
    }
}
