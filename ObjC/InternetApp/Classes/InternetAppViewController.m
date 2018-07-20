#import "InternetAppViewController.h"

@implementation InternetAppViewController

- (void) viewDidLoad
{
	[super viewDidLoad];
	
	
	
	NSURL *url1 = [NSURL URLWithString: @""];
	NSString *string1 = [NSString stringWithContentsOfURL:url1];
	if(string1 != nil && ![string1 isEqualToString:@""])
	{
		[titel setText: string1];
	}
	else
	{
		[titel setText: @"Fehler beim Laden"];
	}
	
	NSURL *url = [NSURL URLWithString: @"http://upload.wikimedia.org/wikipedia/commons/6/63/Wikipedia-logo.png"];
	if(url != nil)
	{
		NSData *urlData = [NSData dataWithContentsOfURL:url];
		UIImage *image = [UIImage imageWithData:urlData];
		[bild setImage:image];
	}
	else
	{
		
	}
	
	NSURL *url2 = [NSURL URLWithString: @"http://de.wikipedia.org/wiki/Wikipedia"];
	NSString *string2 = [NSString stringWithContentsOfURL:url2];
	if(string2 != nil && ![string2 isEqualToString:@""])
	{
		[text setText: string2];
	}
	else
	{
		[text setText: @"Fehler beim Laden"];
	}
	
}

@end
