export class PixelFont {
    public constructor(init?: Partial<PixelFont>) {
        Object.assign(this, init);
    }

    name: string = '';
    size: string = '';
    style: string = '';
    libVersion: string = '';
    fontArray: string = '';
    fileName: string = '';
}
