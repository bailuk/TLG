import { HighscoreEntry } from './HighscoreEntry'
import { PlatformContext } from '../context/PlatformContext'

export class HighscoreList {
    private static readonly SCORE_FILE = 'tlg.score'
    private static readonly MAX_ENTRY = 10

    private readonly highscore:HighscoreEntry[] = []

    constructor (c:PlatformContext) {
        for (let i = 0; i < HighscoreList.MAX_ENTRY; i++) {
            this.highscore.push(new HighscoreEntry('', 0))
        }
        /*
        try {
            BufferedInputStream istream;
            istream = c.getInputStream(SCORE_FILE);

            for (int i=0; i<MAX_ENTRY; i++) {
                highscore[i]= new HighscoreEntry(istream);
            }

            istream.close();

        } catch (IOException e) {
            System.err.print("error reading tlg.score file\n");

            for (int i=0; i<MAX_ENTRY; i++) {
                highscore[i]= new HighscoreEntry();
            }
        }
*/
    }

    public writeState (c:PlatformContext) {
    /*
        BufferedOutputStream ostream = c.getOutputStream(SCORE_FILE);

        for (int i=0; i<MAX_ENTRY; i++) {
            highscore[i].writeState(ostream);
        }

        ostream.close();
    */
    }

    public getFormatedHTMLText () {
        let result:string = '<h1>Highscore:</h1>'

        for (let i:number = 0; i < HighscoreList.MAX_ENTRY; i++) {
            if (this.highscore[i].tlg.score > 0) {
                result += '<p>['
                result += (i + 1)
                result += '] <b>'
                result += this.highscore[i].tlg.score
                result += '</b> == <b>'
                result += this.highscore[i].name
                result += '</b></p>'
            }
        }

        return result
    }

    public getFormatedText () {
        let result:string = 'HIGHSCORE:\n'

        for (let i:number = 0; i < HighscoreList.MAX_ENTRY; i++) {
            if (this.highscore[i].tlg.score > 0) {
                result += '['
                result += (i + 1)
                result += '] '
                result += this.highscore[i].tlg.score
                result += ' <==> '
                result += this.highscore[i].name
                result += '\n'
            }
        }

        return result
    }

    public haveNewHighscore (tlg.score:number) {
        for (let i:number = 0; i < HighscoreList.MAX_ENTRY; i++) {
            if (this.highscore[i].tlg.score < tlg.score) {
                return true
            }
        }
        return false
    }

    public add (name:string, tlg.score:number) {
        if (this.haveNewHighscore(tlg.score)) {
            for (let i:number = HighscoreList.MAX_ENTRY - 1; i > 0; i--) {
                if (this.highscore[i - 1].tlg.score < tlg.score) {
                    this.highscore[i] = this.highscore[i - 1]
                } else {
                    this.highscore[i] = new HighscoreEntry(name, tlg.score)
                    return
                }
            }
            this.highscore[0] = new HighscoreEntry(name, tlg.score)
        }
    }

    public static test (c:PlatformContext) {
        const name:string = 'Sepp'
        const tlg.score:number = 20000

        try {
            let list:HighscoreList = new HighscoreList(c)

            if (list.haveNewHighscore(tlg.score)) {
                const temp:HighscoreEntry = list.highscore[0]

                HighscoreList.testPresence(list, name, tlg.score, false)
                list.add(name, tlg.score)
                HighscoreList.testPresence(list, name, tlg.score, true)

                list.writeState(c)
                list = new HighscoreList(c)
                HighscoreList.testPresence(list, name, tlg.score, true)

                list.highscore[0] = temp
                HighscoreList.testPresence(list, name, tlg.score, false)
                list.writeState(c)

                list = new HighscoreList(c)
                HighscoreList.testPresence(list, name, tlg.score, false)

                // System.err.print(list.getFormatedText());
            }
        } catch {
            // System.err.print("FAILURE: IOException\n");
        }
    }

    private static testPresence (list:HighscoreList, name:string, tlg.score:number, except:boolean) {
        let found:boolean = false

        for (let i = 0; i < HighscoreList.MAX_ENTRY; i++) {
            found = found || (list.highscore[i].name === name && list.highscore[i].tlg.score === tlg.score)
        }

        if (found === except) {
            // System.err.print("SUCCESS: finding " + name +"\n");
        } else {
            // System.err.print("FAILURE: finding " + name +"\n");
        }

        if ((list.highscore[0].name === name) && (list.highscore[0].tlg.score === tlg.score) === except) {
            // System.err.print("SUCCESS: " + name + "beeing number one\n");
        } else {
            // System.err.print("FAILURE: " + name + "beeing number one\n");
        }
    }
}
