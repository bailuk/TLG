import { TlgPoint } from './TlgPoint'
import { MatrixShape } from './MatrixShape'
import { MatrixVisible } from './MatrixVisible'

export class MatrixManipulator extends MatrixVisible {
    public placeShape (b:MatrixShape):boolean {
        if (this.isShapePlaceable(b)) {
            for (let x = 0; x < b.getWidth(); x++) {
                for (let y = 0; y < b.getHeight(); y++) {
                    if (b.getXY(x, y).isActivated()) {
                        this.getD(b.getX() + x, b.getY() + y).set(b.getXY(x, y))
                    }
                }
            }
            return true
        }
        return false
    }

    public removeShape (b:MatrixShape):void {
        for (let x = 0; x < b.getWidth(); x++) {
            for (let y = 0; y < b.getHeight(); y++) {
                if (b.getXY(x, y).isActivated()) {
                    const vx = b.getX() + x
                    const vy = b.getY() + y

                    if (this.isPositionValid(vx, vy)) { this.getD(vx, vy).makeInvisible() }
                }
            }
        }
    }

    private isPositionFree (x:number, y:number):boolean {
        return (this.isPositionValid(x, y) && this.getXY(x, y).isInvisible())
    }

    private isPositionValid (x:number, y:number):boolean {
        return ((x < this.getWidth()) && (y < this.getHeight()) && (x > -1) && (y > -1))
    }

    public moveShape (shape:MatrixShape, pos:TlgPoint):boolean {
        const oldPos = shape.getPos()

        this.removeShape(shape)
        if (this.placeShapeAtPos(shape, pos)) {
            return true
        } else {
            this.placeShapeAtPos(shape, oldPos)
            return false
        }
    }

    private placeShapeAtPos (shape:MatrixShape, pos:TlgPoint):boolean {
        shape.setPos(pos)
        return this.placeShape(shape)
    }

    public moveShapeXY (b:MatrixShape, x:number, y:number):boolean {
        return this.moveShape(b, new TlgPoint(x, y))
    }

    public isShapePlaceable (shape:MatrixShape):boolean {
        let r:boolean = true

        for (let x = 0; r && x < shape.getWidth(); x++) {
            for (let y = 0; r && y < shape.getHeight(); y++) {
                if (shape.getXY(x, y).isVisible()) {
                    r = this.isPositionFree(shape.getX() + x, shape.getY() + y)
                }
            }
        }
        return r
    }

    public autoPlaceShape (shape:MatrixShape):boolean {
        let result:boolean = false
        const center = (this.getWidth() / 2) - (shape.getWidth() / 2)
        shape.autoOffset()

        for (let xoffset = 0; !result && xoffset < (this.getWidth() - center); xoffset++) {
            result = (this.placeShapeHorizontal(shape, center + xoffset) ||
                    this.placeShapeHorizontal(shape, center - xoffset))
        }
        return result
    }

    private placeShapeHorizontal (shape:MatrixShape, xpos:number):boolean {
        shape.setX(xpos)
        return this.placeShape(shape)
    }
}
