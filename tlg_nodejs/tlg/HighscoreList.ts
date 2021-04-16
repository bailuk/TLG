import { HighscoreEntry } from './HighscoreEntry';
import { PlatformContext } from './PlatformContext';

export class HighscoreList
{
    private static readonly SCORE_FILE = "score";
    private static readonly MAX_ENTRY = 10;


    private readonly highscore:HighscoreEntry[] = [];


    constructor(c:PlatformContext) {
        for (var i=0; i<HighscoreList.MAX_ENTRY; i++) {
            this.highscore.push(new HighscoreEntry("", 0));
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
            System.err.print("error reading score file\n");

            for (int i=0; i<MAX_ENTRY; i++) {
                highscore[i]= new HighscoreEntry();
            }
        }
*/
    }


    public writeState(c:PlatformContext) {
    /*
        BufferedOutputStream ostream = c.getOutputStream(SCORE_FILE);
        
        for (int i=0; i<MAX_ENTRY; i++) {
            highscore[i].writeState(ostream);
        }
        
        ostream.close();
    */
    }


    public getFormatedHTMLText() {
        let result:string = "<h1>Highscore:</h1>";
                
        for (let i:number=0; i < HighscoreList.MAX_ENTRY; i++) {
            if (this.highscore[i].score > 0) {
                result += "<p>[";
                result += (i+1);
                result += "] <b>";
                result += this.highscore[i].score;
                result += "</b> == <b>";
                result += this.highscore[i].name;
                result += "</b></p>";
            }
        }

        return result;
    }

    public getFormatedText() {
        let result:string = "HIGHSCORE:\n";
        
        for (let i:number=0; i < HighscoreList.MAX_ENTRY; i++) {
            
            if (this.highscore[i].score >0 ) {
                result += "[";
                result += (i+1);
                result += "] ";
                result += this.highscore[i].score;
                result += " <==> ";
                result += this.highscore[i].name;
                result += "\n";
            }
        }
        
        return result;
    }

    
    public haveNewHighscore(score:number) {
        for (let i:number=0; i<HighscoreList.MAX_ENTRY; i++) {
            if (this.highscore[i].score < score) {
                return true;
            }
        }
        return false;
    }
    
    
    public add(name:string, score:number) {
        if (this.haveNewHighscore(score)) {
            for (let i:number=HighscoreList.MAX_ENTRY-1; i>0; i--) {
                if (this.highscore[i-1].score < score) { 
                    this.highscore[i] = this.highscore[i-1];
                } else {
                    this.highscore[i] = new HighscoreEntry(name, score);
                    return;
                }
                
            }
            this.highscore[0] = new HighscoreEntry(name, score);
        }
    }

    
    public static test(c:PlatformContext) {
        const name:string = "Sepp";
        const score:number = 20000;
        
        try {

            let list:HighscoreList = new HighscoreList(c);

            if (list.haveNewHighscore(score)) {

                let temp:HighscoreEntry = list.highscore[0];

                HighscoreList.testPresence(list, name, score, false);
                list.add(name, score);
                HighscoreList.testPresence(list, name, score, true);


                list.writeState(c);
                list = new HighscoreList(c);
                HighscoreList.testPresence(list, name, score, true);

                list.highscore[0]=temp;
                HighscoreList.testPresence(list, name, score, false);
                list.writeState(c);

                list = new HighscoreList(c);
                HighscoreList.testPresence(list, name, score, false);
                
                
                //System.err.print(list.getFormatedText());

            }
        } catch  {
            //System.err.print("FAILURE: IOException\n");
        }
    }

    private static testPresence(list:HighscoreList, name:string, score:number, except:boolean) {
        let found:boolean = false;
        
        for (let i=0; i<HighscoreList.MAX_ENTRY; i++) {
            found = found || (list.highscore[i].name === name && list.highscore[i].score == score);
        }
        
        if (found == except) {
            //System.err.print("SUCCESS: finding " + name +"\n");
        } else  {
            //System.err.print("FAILURE: finding " + name +"\n");
        }
        
        if ( (list.highscore[0].name === name) && (list.highscore[0].score==score) == except) {
            //System.err.print("SUCCESS: " + name + "beeing number one\n");
        } else  {
            //System.err.print("FAILURE: " + name + "beeing number one\n");
        }
    }

}
