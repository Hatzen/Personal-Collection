#import <Foundation/Foundation.h>

@interface objekt : NSObject {
	int ident;
	NSString* titel;
	UIImage* bild;
	NSString* text;
}

@property(nonatomic, retain) NSString* titel;;
@property(nonatomic,retain) UIImage* bild;
@property(nonatomic,retain) NSString* text;
@property(nonatomic, assign) int ident;

- (id) initWithContent: (int) ident2: (NSString*) titel2: (UIImage*) url2: (NSString*) text2;

@end
