import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Font } from 'src/app/shared/font';
import { FontsService } from 'src/app/shared/fonts.service';
import { PixelFont } from 'src/app/shared/pixel-font';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-font-form',
  templateUrl: './font-form.component.html',
  styleUrls: ['./font-form.component.css']
})
export class FontFormComponent implements OnInit  {
  fontForm = this.fb.group({
    previewDisplay: 'OLED96',
    name: 'Dialog',
    style: '0',
    size: '16',
    libVersion: '3',
  });
  

  hasUnitNumber = false;

  previewDisplays = [
    {name: 'OLED 0.96" (128x64)', value: 'OLED96'},
    {name: 'TFT 2.4" (240x320)', value: 'TFT24'},
  ];
  styles = {
    "0": {name:"Plain"},
    "1": {name:"Bold"},
    "2": {name:"Italic"},
    "3": {name:"Bold & Italic"}
  };

  libraryVersions = {
    "2": "<3.0.0",
    "3": ">=3.0.0",
    "gfx": "Adafruit GFX Font"
  };

  fonts!: Font[];

  fontFamilies!: string[];

  pixelFont: PixelFont = new PixelFont();

  constructor(public fontsService: FontsService, private fb: FormBuilder) {}
  ngOnInit(): void {
    this.fontsService.findAll().subscribe((data: Font[]) => {
      this.fonts = data;
      this.fontFamilies = Array.from(new Set(this.fonts.map((item) => item.fontFamily))).sort();
      console.log(this.fontFamilies);
    })
  }

  onSubmit(): void {
    this.pixelFont = Object.assign(this.pixelFont, this.fontForm.value);
    this.fontsService.convertToPixelFont(this.pixelFont).subscribe((data: PixelFont) => {
      this.pixelFont = data;
    });
  }

  getImageUrl(): string {
    let formValue = this.fontForm.value;
    return '/rest/fontPreview/' + formValue.name 
    + '/' + formValue.style + '/'+ formValue.size +'/' + formValue.previewDisplay;
    
  }

  onBeer(): void {
    window.open("https://www.paypal.com/paypalme/squix78");
  }

  getFontArray(): string {
    return this.pixelFont.fontArray;
  }
}
