package ch.squix.esp8266.fontconverter.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

@Slf4j
@Component
public class FontRepository {

    private final static String fonts [] = {
        "/meteocons/Meteocons.ttf",
        "/meteocons/Moonphases.ttf",
        "/apache/redressed/Redressed-Regular.ttf",
        "/apache/opensanscondensed/OpenSansCondensed-Bold.ttf",
        "/apache/opensanscondensed/OpenSansCondensed-LightItalic.ttf",
        "/apache/opensanscondensed/OpenSansCondensed-Light.ttf",
        "/apache/tinos/Tinos-Regular.ttf",
        "/apache/tinos/Tinos-Italic.ttf",
        "/apache/tinos/Tinos-Bold.ttf",
        "/apache/tinos/Tinos-BoldItalic.ttf",
        "/apache/rochester/Rochester-Regular.ttf",
        "/apache/jsmathcmsy10/jsMath-cmsy10.ttf",
        "/apache/smokum/Smokum-Regular.ttf",
        "/apache/calligraffitti/Calligraffitti-Regular.ttf",
        "/apache/robotoslab/RobotoSlab-Thin.ttf",
        "/apache/robotoslab/RobotoSlab-Bold.ttf",
        "/apache/robotoslab/RobotoSlab-Light.ttf",
        "/apache/robotoslab/RobotoSlab-Regular.ttf",
        "/apache/ultra/Ultra-Regular.ttf",
        "/apache/syncopate/Syncopate-Bold.ttf",
        "/apache/syncopate/Syncopate-Regular.ttf",
        "/apache/chewy/Chewy-Regular.ttf",
        "/apache/homemadeapple/HomemadeApple-Regular.ttf",
        "/apache/arimo/Arimo-Bold.ttf",
        "/apache/arimo/Arimo-Italic.ttf",
        "/apache/arimo/Arimo-Regular.ttf",
        "/apache/arimo/Arimo-BoldItalic.ttf",
        "/apache/jsmathcmr10/jsMath-cmr10.ttf",
        "/apache/maidenorange/MaidenOrange-Regular.ttf",
        "/apache/jsmathcmti10/jsMath-cmti10.ttf",
        "/apache/nokora/Nokora-Regular.ttf",
        "/apache/nokora/Nokora-Bold.ttf",
        "/apache/unkempt/Unkempt-Bold.ttf",
        "/apache/unkempt/Unkempt-Regular.ttf",
        "/apache/montez/Montez-Regular.ttf",
        "/apache/opensanshebrewcondensed/OpenSansHebrewCondensed-Italic.ttf",
        "/apache/opensanshebrewcondensed/OpenSansHebrewCondensed-ExtraBoldItalic.ttf",
        "/apache/opensanshebrewcondensed/OpenSansHebrewCondensed-LightItalic.ttf",
        "/apache/opensanshebrewcondensed/OpenSansHebrewCondensed-Regular.ttf",
        "/apache/opensanshebrewcondensed/OpenSansHebrewCondensed-ExtraBold.ttf",
        "/apache/opensanshebrewcondensed/OpenSansHebrewCondensed-BoldItalic.ttf",
        "/apache/opensanshebrewcondensed/OpenSansHebrewCondensed-Light.ttf",
        "/apache/opensanshebrewcondensed/OpenSansHebrewCondensed-Bold.ttf",
        "/apache/mountainsofchristmas/MountainsofChristmas-Regular.ttf",
        "/apache/mountainsofchristmas/MountainsofChristmas-Bold.ttf",
        "/apache/luckiestguy/LuckiestGuy-Regular.ttf",
        "/apache/justanotherhand/JustAnotherHand-Regular.ttf",
        "/apache/yellowtail/Yellowtail-Regular.ttf",
        "/apache/robotocondensed/RobotoCondensed-Bold.ttf",
        "/apache/robotocondensed/RobotoCondensed-Light.ttf",
        "/apache/robotocondensed/RobotoCondensed-Italic.ttf",
        "/apache/robotocondensed/RobotoCondensed-BoldItalic.ttf",
        "/apache/robotocondensed/RobotoCondensed-LightItalic.ttf",
        "/apache/robotocondensed/RobotoCondensed-Regular.ttf",
        "/apache/specialelite/SpecialElite-Regular.ttf",
        "/apache/jsmathcmbx10/jsMath-cmbx10.ttf",
        "/apache/jsmathcmmi10/jsMath-cmmi10.ttf",
        "/apache/aclonica/Aclonica-Regular.ttf",
        "/apache/rancho/Rancho-Regular.ttf",
        "/apache/sunshiney/Sunshiney-Regular.ttf",
        "/apache/walterturncoat/WalterTurncoat-Regular.ttf",
        "/apache/cousine/Cousine-BoldItalic.ttf",
        "/apache/cousine/Cousine-Bold.ttf",
        "/apache/cousine/Cousine-Italic.ttf",
        "/apache/cousine/Cousine-Regular.ttf",
        "/apache/permanentmarker/PermanentMarker-Regular.ttf",
        "/apache/jsmathcmex10/jsMath-cmex10.ttf",
        "/apache/creepstercaps/CreepsterCaps-Regular.ttf",
        "/apache/cherrycreamsoda/CherryCreamSoda-Regular.ttf",
        "/apache/opensanshebrew/OpenSansHebrew-ExtraBoldItalic.ttf",
        "/apache/opensanshebrew/OpenSansHebrew-BoldItalic.ttf",
        "/apache/opensanshebrew/OpenSansHebrew-Regular.ttf",
        "/apache/opensanshebrew/OpenSansHebrew-ExtraBold.ttf",
        "/apache/opensanshebrew/OpenSansHebrew-Light.ttf",
        "/apache/opensanshebrew/OpenSansHebrew-LightItalic.ttf",
        "/apache/opensanshebrew/OpenSansHebrew-Bold.ttf",
        "/apache/opensanshebrew/OpenSansHebrew-Italic.ttf",
        "/apache/robotomono/RobotoMono-Medium.ttf",
        "/apache/robotomono/RobotoMono-Regular.ttf",
        "/apache/robotomono/RobotoMono-LightItalic.ttf",
        "/apache/robotomono/RobotoMono-BoldItalic.ttf",
        "/apache/robotomono/RobotoMono-ThinItalic.ttf",
        "/apache/robotomono/RobotoMono-Light.ttf",
        "/apache/robotomono/RobotoMono-MediumItalic.ttf",
        "/apache/robotomono/RobotoMono-Bold.ttf",
        "/apache/robotomono/RobotoMono-Italic.ttf",
        "/apache/robotomono/RobotoMono-Thin.ttf",
        "/apache/satisfy/Satisfy-Regular.ttf",
        "/apache/rocksalt/RockSalt-Regular.ttf",
        "/apache/roboto/Roboto-Medium.ttf",
        "/apache/roboto/Roboto-Light.ttf",
        "/apache/roboto/Roboto-Regular.ttf",
        "/apache/roboto/Roboto-MediumItalic.ttf",
        "/apache/roboto/Roboto-ThinItalic.ttf",
        "/apache/roboto/Roboto-BoldItalic.ttf",
        "/apache/roboto/Roboto-LightItalic.ttf",
        "/apache/roboto/Roboto-Italic.ttf",
        "/apache/roboto/Roboto-BlackItalic.ttf",
        "/apache/roboto/Roboto-Bold.ttf",
        "/apache/roboto/Roboto-Thin.ttf",
        "/apache/roboto/Roboto-Black.ttf",
        "/apache/irishgrover/IrishGrover-Regular.ttf",
        "/apache/comingsoon/ComingSoon-Regular.ttf",
        "/apache/craftygirls/CraftyGirls-Regular.ttf",
        "/apache/schoolbell/Schoolbell-Regular.ttf",
        "/apache/crushed/Crushed-Regular.ttf",
        "/apache/slackey/Slackey-Regular.ttf",
        "/apache/kranky/Kranky-Regular.ttf",
        "/apache/opensans/OpenSans-SemiBold.ttf",
        "/apache/opensans/OpenSans-Light.ttf",
        "/apache/opensans/OpenSans-Italic.ttf",
        "/apache/opensans/OpenSans-ExtraBold.ttf",
        "/apache/opensans/OpenSans-LightItalic.ttf",
        "/apache/opensans/OpenSans-Bold.ttf",
        "/apache/opensans/OpenSans-SemiBoldItalic.ttf",
        "/apache/opensans/OpenSans-ExtraBoldItalic.ttf",
        "/apache/opensans/OpenSans-Regular.ttf",
        "/apache/opensans/OpenSans-BoldItalic.ttf",
        "/apache/fontdinerswanky/FontdinerSwanky-Regular.ttf",
        "/dseg/DSEG14-Modern/DSEG14Modern-LightItalic.ttf",
        "/dseg/DSEG14-Modern/DSEG14Modern-Italic.ttf",
        "/dseg/DSEG14-Modern/DSEG14Modern-Regular.ttf",
        "/dseg/DSEG14-Modern/DSEG14Modern-BoldItalic.ttf",
        "/dseg/DSEG14-Modern/DSEG14Modern-RegularItalic.ttf",
        "/dseg/DSEG14-Modern/DSEG14Modern-Light.ttf",
        "/dseg/DSEG14-Modern/DSEG14Modern-Bold.ttf",
        "/dseg/DSEG14-Classic/DSEG14Classic-MINIItalic.ttf",
        "/dseg/DSEG14-Classic/DSEG14Classic-RegularItalic.ttf",
        "/dseg/DSEG14-Classic/DSEG14Classic-Light.ttf",
        "/dseg/DSEG14-Classic/DSEG14Classic-Italic.ttf",
        "/dseg/DSEG14-Classic/DSEG14Classic-Bold.ttf",
        "/dseg/DSEG14-Classic/DSEG14Classic-LightItalic.ttf",
        "/dseg/DSEG14-Classic/DSEG14Classic-BoldItalic.ttf",
        "/dseg/DSEG14-Classic/DSEG14Classic-Regular.ttf",
        "/dseg/DSEG14-Modern-MINI/DSEG14ModernMini-BoldItalic.ttf",
        "/dseg/DSEG14-Modern-MINI/DSEG14ModernMini-Regular.ttf",
        "/dseg/DSEG14-Modern-MINI/DSEG14ModernMini-RegularItalic.ttf",
        "/dseg/DSEG14-Modern-MINI/DSEG14ModernMini-Bold.ttf",
        "/dseg/DSEG14-Modern-MINI/DSEG14ModernMini-Light.ttf",
        "/dseg/DSEG14-Modern-MINI/DSEG14ModernMini-Italic.ttf",
        "/dseg/DSEG14-Modern-MINI/DSEG14ModernMini-LightItalic.ttf",
        "/dseg/DSEG7-Classic/DSEG7Classic-Regular.ttf",
        "/dseg/DSEG7-Classic/DSEG7Classic-LightItalic.ttf",
        "/dseg/DSEG7-Classic/DSEG7Classic-Bold.ttf",
        "/dseg/DSEG7-Classic/DSEG7Classic-Light.ttf",
        "/dseg/DSEG7-Classic/DSEG7Classic-RegularItalic.ttf",
        "/dseg/DSEG7-Classic/DSEG7Classic-Italic.ttf",
        "/dseg/DSEG7-Classic/DSEG7Classic-BoldItalic.ttf",
        "/dseg/DSEGWeather/DSEGWeather.ttf",
        "/dseg/DSEG14-Classic-MINI/DSEG14ClassicMini-BoldItalic.ttf",
        "/dseg/DSEG14-Classic-MINI/DSEG14ClassicMini-Italic.ttf",
        "/dseg/DSEG14-Classic-MINI/DSEG14ClassicMini-Bold.ttf",
        "/dseg/DSEG14-Classic-MINI/DSEG14ClassicMini-Light.ttf",
        "/dseg/DSEG14-Classic-MINI/DSEG14ClassicMini-Regular.ttf",
        "/dseg/DSEG14-Classic-MINI/DSEG14ClassicMini-LightItalic.ttf",
        "/dseg/DSEG7-7SEGG-CHAN/DSEG77SEGGCHAN-Regular.ttf",
        "/dseg/DSEG7-7SEGG-CHAN/DSEG7SEGGCHANMINI-Regular.ttf",
        "/dseg/DSEG7-7SEGG-CHAN/DSEG7SEGGCHAN-Regular.ttf",
        "/dseg/DSEG7-7SEGG-CHANMINI/DSEG77SEGGCHANMINI-Regular.ttf",
        "/dseg/DSEG7-Modern/DSEG7Modern-RegularItalic.ttf",
        "/dseg/DSEG7-Modern/DSEG7Modern-LightItalic.ttf",
        "/dseg/DSEG7-Modern/DSEG7Modern-Italic.ttf",
        "/dseg/DSEG7-Modern/DSEG7Modern-Regular.ttf",
        "/dseg/DSEG7-Modern/DSEG7Modern-Bold.ttf",
        "/dseg/DSEG7-Modern/DSEG7Modern-BoldItalic.ttf",
        "/dseg/DSEG7-Modern/DSEG7Modern-Light.ttf",
        "/dseg/DSEG7-Modern-MINI/DSEG7ModernMini-Regular.ttf",
        "/dseg/DSEG7-Modern-MINI/DSEG7ModernMini-Italic.ttf",
        "/dseg/DSEG7-Modern-MINI/DSEG7ModernMini-Bold.ttf",
        "/dseg/DSEG7-Modern-MINI/DSEG7ModernMini-RegularItalic.ttf",
        "/dseg/DSEG7-Modern-MINI/DSEG7ModernMini-Light.ttf",
        "/dseg/DSEG7-Modern-MINI/DSEG7ModernMini-BoldItalic.ttf",
        "/dseg/DSEG7-Modern-MINI/DSEG7ModernMini-LightItalic.ttf",
        "/dseg/DSEG7-Classic-MINI/DSEG7ClassicMini-Regular.ttf",
        "/dseg/DSEG7-Classic-MINI/DSEG7ClassicMini-BoldItalic.ttf",
        "/dseg/DSEG7-Classic-MINI/DSEG7ClassicMini-Light.ttf",
        "/dseg/DSEG7-Classic-MINI/DSEG7ClassicMini-Italic.ttf",
        "/dseg/DSEG7-Classic-MINI/DSEG7ClassicMini-RegularItalic.ttf",
        "/dseg/DSEG7-Classic-MINI/DSEG7ClassicMini-Bold.ttf",
        "/dseg/DSEG7-Classic-MINI/DSEG7ClassicMini-LightItalic.ttf",
        "/orbitron/Orbitron-Black.ttf",
        "/orbitron/Orbitron-Bold.ttf",
        "/orbitron/Orbitron-Medium.ttf",
        "/orbitron/Orbitron-Regular.ttf",
        "/fonts/gothic/urw_gothic_l_book.ttf"

    };

    @Bean 
    void registerFonts() {
        for (String font : fonts) {
            registerFont(font);
        }

        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font font : ge.getAllFonts()) {
            log.info("Loadded font {}", font);
        }

    }

    public void registerFont(final String fontPath) {
        try {
            InputStream is = FontRepository.class.getResourceAsStream(fontPath);
            Font fileFont = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fileFont);

        } catch (Exception e) {
            log.error("Failed to register fonts", e);
        }
    }


}
