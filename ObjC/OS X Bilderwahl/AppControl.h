#import <Cocoa/Cocoa.h>

@interface AppControl : NSObject {
    IBOutlet id datei;
    IBOutlet id image;
    IBOutlet id label;
    IBOutlet id bildunterschrift;
    IBOutlet id frame;
	IBOutlet id fehler;
}
- (IBAction)auswahl:(id)sender;
- (IBAction)upload:(id)sender;
@end
