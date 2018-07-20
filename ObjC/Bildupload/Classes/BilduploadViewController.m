#import "BilduploadViewController.h"
#import "bildanzeige.h"
#import "bildadd.h"
#import "Statics.h"

@implementation BilduploadViewController


- (void)viewDidLoad
{
	[super viewDidLoad];
	lager = [[store alloc] init];
	[self loadData];
	//NSLog(@"jap");
}

- (IBAction)add:(id)sender
{
	bildadd* screen = [[bildadd alloc] initWithNibName:nil bundle:nil];
	[[Statics class] randomTransition: screen];
	[self presentModalViewController:screen animated: YES];
	[screen release];	
}

- (IBAction)refresh:(id)sender
{
	[self loadData];
}

- (void) didReceiveMemoryWarning
{
	NSLog(@"Sbaicha");
}

- (void) loadData
{
	alert = [[[UIAlertView alloc] initWithTitle:@"Daten werden Runtergeladen\nBitte warten..." message:nil delegate:self cancelButtonTitle:nil otherButtonTitles: nil] autorelease];
	[alert show];
	
	UIActivityIndicatorView *indicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
	
	// Adjust the indicator so it is up a few pixels from the bottom of the alert
	indicator.center = CGPointMake(alert.bounds.size.width / 2, alert.bounds.size.height - 50);
	[indicator startAnimating];
	[alert addSubview:indicator];
	[indicator release];
	
	
	[self performSelectorInBackground:@selector(performBackground) withObject:nil];
	
	
	//[self performSelectorOnMainThread:@selector(refresh:) withObject:nil waitUntilDone:YES];
}

-(void) performBackground
{
	[lager load];
	[self performSelectorOnMainThread:@selector(exit) withObject:nil waitUntilDone:YES ];
}

-(void) exit
{
	array = [[NSArray alloc] initWithArray: [lager daten]];
	[alert dismissWithClickedButtonIndex:0 animated:YES];
	[table reloadData];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [array count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *simpleTableIdentifier = @"SimpleTableItem";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
	
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
    }
    cell.textLabel.text = [[array objectAtIndex:indexPath.row] titel];
	cell.imageView.image = [[array objectAtIndex:indexPath.row] bild];
    return cell;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	return YES;
}

- (void) tableView: (UITableView *) tableView didSelectRowAtIndexPath: (NSIndexPath *) indexPath 
{ 
	//NSLog(@"index : %i", indexPath.row);
	//NSLog(@"addresse : %i", [[lager daten] count]);
	bildanzeige* screen = [[bildanzeige alloc] initWithNibName:nil bundle:nil];
	[[Statics class] randomTransition: screen];
	[self presentModalViewController:screen animated: YES];
	[screen setAnzeige:[[lager daten] objectAtIndex: indexPath.row] ];
	[screen release];	
	
	/*NSLog(@"titel : %@", [[[lager daten] objectAtIndex: indexPath.row] titel]);
	NSLog(@"id: %i", [[[lager daten] objectAtIndex: indexPath.row] ident]);
	NSLog(@"Text : %@", [[[lager daten] objectAtIndex: indexPath.row] text]);
	NSLog(@"img : %@", [[[lager daten] objectAtIndex: indexPath.row] bild]);*/
}
@end
