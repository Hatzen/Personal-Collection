#import "store.h"
#import "Statics.h"

@implementation store

static NSMutableArray* daten;

- (id) init
{
	if(self = [super init])
	{
		daten = [[NSMutableArray alloc] init];
	}
	return self;
}

- (NSArray*) daten
{
	return daten;
}

- (void) load
{
	//NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
	[daten removeAllObjects];
	NSString* content = [NSString stringWithContentsOfURL:[NSURL URLWithString:[NSString stringWithFormat: @"http://hartzkai.freehostia.com/listData.php?pass=%@",[[Statics class] pass]] ]];
	content = [content substringFromIndex:3];
	//NSLog(@"%@",content);
	NSArray* array = [content componentsSeparatedByString:@"|||"];
	//[content release];
	if(([array count] % 4) != 0)
	{
		NSLog(@"Länge nicht korrekt:  %i" , [array count]);
	}
	for(int i = 0 ; i+4 <= [array count]; i += 4)
	{		
		int ident = [[array objectAtIndex:i] intValue];
		NSString* titel = [array objectAtIndex:i+1];
		
		NSString* string = @"http://hartzkai.freehostia.com";
		string = [string stringByAppendingString:[array objectAtIndex:i+2]];
		//NSLog(@"string: %@", string);
		
		NSURL* url = [[NSURL alloc] initWithString: string];
		//NSURL* url = [[NSURL alloc] initWithString: @"http://upload.wikimedia.org/wikipedia/commons/6/63/Wikipedia-logo.png"];
		//NSLog(@"url: %@",url);
		
		
		NSError* error = nil;
			NSData *urlData = [NSData dataWithContentsOfURL:url options:0 error:&error];
			//NSLog(@" error: %@", error );
			//NSLog(@"data: %@",urlData);
			UIImage *bild = [UIImage imageWithData:urlData];
			//NSLog(@"bild : %@ ", bild);
		
		//[string release];
		//[url release];
		//[urlData release];
		
		NSString* text = [array objectAtIndex:i+3];
		
		//NSLog(@"  %i von %i" , i, [array count]);
		
		
		//[[objekt alloc] initWithContent: ident : titel : bild : text];
		[daten addObject:[[objekt alloc] initWithContent: ident : titel : bild : text]];
		/*[titel retain];
		[bild retain];
		[text retain];
		[titel release];
		[bild release];
		[text release];*/
	}
	//NSLog(@"Durch");
	//[pool drain];
}
@end
