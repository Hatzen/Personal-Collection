#import "objekt.h"

@implementation objekt

@synthesize ident;
@synthesize titel;
@synthesize bild;
@synthesize text;

- (id) initWithContent: (int) ident2: (NSString*) titel2: (UIImage*) url2: (NSString*) text2;
{
	ident = ident2;
	
	[titel2 retain];
	titel = titel2;
	
	[url2 retain];
	bild = url2;
	
	[text2 retain];
	text = text2;
	
	//NSLog("%@", text);
	return self;
}

- (NSString*) description
{
	NSMutableString* string = @"";
	//[string appendString:ident];
	[string appendString:titel];
	[string appendString:text];
	return [NSString stringWithString:string];
}

@end
