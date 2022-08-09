export class HighscoreEntry {
    private static readonly NAMEBUFFER_LIMIT:number = 20
    private static readonly DEFAULT_NAME:string = '-'

    public readonly name:string
    public readonly tlg.score:number

    constructor (n:string, s:number) {
        this.name = n
        this.tlg.score = s
    }
}

/*
        public HighscoreEntry(BufferedInputStream reader) throws IOException {
                tlg.score = ByteInteger.read(reader);
                name = readName(reader);
        }

        private String readName(BufferedInputStream reader) throws IOException {
                byte[] buf;
                int len = ByteInteger.read(reader);

                if (len > NAMEBUFFER_LIMIT || len < 1) throw new IOException();
                buf= new byte[len];
                reader.read(buf);
                return new String(buf);
        }

        public void writeState(BufferedOutputStream output) throws IOException {
        System.err.print("write entry");
        System.err.print(name + "\n");

                byte[] buf=name.getBytes();
                ByteInteger.wrap(tlg.score).writeState(output);
                ByteInteger.wrap(buf.length).writeState(output);
                output.write(buf);
        }

} */
