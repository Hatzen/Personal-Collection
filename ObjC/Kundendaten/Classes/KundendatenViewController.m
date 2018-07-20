#import "KundendatenViewController.h"
#import "tableCell.h"
#import <QuartzCore/QuartzCore.h>

@implementation KundendatenViewController

static const int CELLWIDTH = 120;
static const float ELEMENTFONTSIZE = 17.0f;
static const int PADDINGLEFT = 17;

static dbConnect* connect;

-(void) viewDidLoad
{
	selected = -1;
	connect = [[dbConnect alloc] init];
	[connect openDb];
	contentStrings = [connect getItems];
	/*//Get MYSQL DATEN
	contentStrings = [[NSMutableArray alloc] init];
	for(int i = 0 ; i < 20 ; i++ )
	{
		NSMutableArray* tmp = [[NSMutableArray alloc] init ];
		for(int z = 0 ; z < 7 ; z++ )
		{
			[tmp addObject:[[NSString alloc] initWithFormat:@"Z: %i  S: %i ", i , z]];
		}
		[contentStrings addObject:tmp];
	}*/
	
	[self calculateCellWidth];
	
	titelStrings = [contentStrings objectAtIndex:0];
	[contentStrings removeObject: titelStrings];
	/*
	for(int i = 0 ; i < [[contentStrings objectAtIndex:0] count] ; i++ )
	{
		[titelStrings addObject:[NSString stringWithFormat:@"Element %i", i] ];
	}*/
	//ENDE
	
	[self refreshTitelBar];
	
	anzeigeOptionen = [[UIAlertView alloc] initWithTitle:@"Anzeige Optionen"
													  message:@"Bitte auswählen."
													 delegate:self
											cancelButtonTitle:@"Abbrechen"
											otherButtonTitles:nil];
	[anzeigeOptionen addButtonWithTitle:@"Tabelle wählen"];
	[anzeigeOptionen addButtonWithTitle:@"Sortieren"];
	[tabBar setDelegate:self];
	[super viewDidLoad];
	[self refreshViews];
}

-(void) viewDidAppear:(BOOL)animated
{
	[super viewDidAppear:animated];
	[self refresh];
}

-(void) refreshViews
{
	[tabelle setAutoresizingMask:UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleWidth];
	[tabelle setFrame: CGRectMake([tabelle frame].origin.x, [tabelle frame].origin.y, [self sum:-1], [tabelle frame].size.height)];
	[scrollView setContentSize:CGSizeMake([self sum:-1], [tabelle frame].size.height)];
	[head setFrame: CGRectMake([head frame].origin.x, [head frame].origin.y, [self sum:-1], [head frame].size.height)];
}

-(void) refreshTitelBar
{
	for(id x in [head subviews])
	{
		if([x isKindOfClass:[UIView class]])
		{
			[x removeFromSuperview];
		}
	}
	for(int i = 0 ; i < [titelStrings count] ; i++ )
	{
		CGRect rect = CGRectMake( [self sum:i] , 0,[[cellWidths objectAtIndex:i] intValue], [head frame].size.height ) ;
		UIView* shape = [[UILabel alloc] initWithFrame:rect];
        [shape setAutoresizingMask:(UIViewAutoresizingFlexibleRightMargin | UIViewAutoresizingFlexibleHeight)];
		[shape setBackgroundColor: [UIColor colorWithPatternImage:[UIImage imageNamed:@"gradient.png"]]];
		
		CALayer* layer = [shape layer];
		layer.borderColor = [UIColor darkGrayColor].CGColor;
		layer.borderWidth = 1;
		layer.frame = CGRectMake(layer.frame.origin.x-1, -1, layer.frame.size.width+1, layer.frame.size.height+1);
		
		
		CGRect rectLabel = CGRectMake( PADDINGLEFT, 0 , [shape frame].size.width-2*PADDINGLEFT, [shape frame].size.height) ;
		UILabel* label = [[UILabel alloc] initWithFrame:rectLabel];
		[label setText: [titelStrings objectAtIndex:i]];
		[label setTextAlignment:UITextAlignmentCenter];
		[label setFont:[UIFont systemFontOfSize:ELEMENTFONTSIZE]];
        [label setTextColor:[UIColor whiteColor]];
		[label setOpaque:NO];
        [label setBackgroundColor:nil];
		
		[shape addSubview:label];
		[head addSubview:shape];
	}
}

-(void) refreshCell: (UITableViewCell*) cell: (NSIndexPath *)indexPath
{
	//NSLog(@"error voraus :)");
	for(id x in [[cell contentView] subviews])
	{
		if([x isKindOfClass:[UIView class]])
		{
			[x removeFromSuperview];
		}
	}
	for(int z = 0 ; z < [[contentStrings objectAtIndex:0] count] ; z++ )
	{
		
		CGRect rect = CGRectMake( [self sum:z], 0,[[cellWidths objectAtIndex:z] intValue], [tabelle rowHeight] ) ;
		UIView* shape = [[UILabel alloc] initWithFrame:rect];
		[shape setAutoresizingMask:(UIViewAutoresizingFlexibleRightMargin | UIViewAutoresizingFlexibleHeight)];
		if((z % 2) == 0)
			[shape setBackgroundColor:[UIColor colorWithRed: 164.0f/255.0f green:211.0f/255.0f blue:238.0f/255.0f alpha: 1.0f]];
		else	
			[shape setBackgroundColor:[UIColor whiteColor]];
		
		CALayer* layer = [shape layer];
		layer.borderColor = [UIColor lightGrayColor].CGColor;
		layer.borderWidth = 1;
		layer.cornerRadius = 0;
		layer.frame = CGRectMake(layer.frame.origin.x-1, -1, layer.frame.size.width+1, layer.frame.size.height+1);
		
		
		CGRect rectLabel = CGRectMake( PADDINGLEFT, 0 , [shape frame].size.width-2*PADDINGLEFT, [shape frame].size.height) ;
		UILabel* label = [[UILabel alloc] initWithFrame:rectLabel];
		[label setText: [[contentStrings objectAtIndex:indexPath.row] objectAtIndex: z ]];
		[label setFont:[UIFont systemFontOfSize:ELEMENTFONTSIZE]];
		[label setTextColor:[UIColor darkGrayColor]];
		[label setOpaque:NO];
		[label setBackgroundColor: nil];
		
		[shape addSubview:label];
		[[cell contentView] addSubview:shape];
	}
	
	//NSLog(@"doch kein error voraus :)");
}

-(void) refresh
{
	//NSLog(@"1");
	contentStrings = [connect getItems];
	//NSLog(@"2");
	[self calculateCellWidth];
	//NSLog(@"3");
	titelStrings = [contentStrings objectAtIndex:0];
	//NSLog(@"4");
	[contentStrings removeObject: titelStrings];
	//NSLog(@"5");
	[self refreshTitelBar];
	//NSLog(@"6");
	[self refreshViews];
	//NSLog(@"7");
	[tabelle reloadData];
	//NSLog(@"refreshed");
}

+ (dbConnect*) connect
{
	return connect;
}

- (void) calculateCellWidth
{
	cellWidths = [[NSMutableArray alloc] init];
	int MIN_WIDTH = 75;
	int MAX_WIDTH = 300;
	for(int i = 0 ; i < [[contentStrings objectAtIndex:0] count] ; i++)
	{
		int max = 0;
		for(int z = 0 ; z < [contentStrings count] ; z++)
		{
			int cellWidth = [[[contentStrings objectAtIndex:z] objectAtIndex:i] sizeWithFont:[UIFont systemFontOfSize:ELEMENTFONTSIZE]].width+2*PADDINGLEFT;
			if(cellWidth > max)
			{
				max = cellWidth;
			}
		}
		if(MIN_WIDTH > max)
		{
			max = MIN_WIDTH;
		}
		if(MAX_WIDTH < max)
		{
			max = MAX_WIDTH;
		}
		[cellWidths addObject: [NSNumber numberWithInt:max]]; 
	}
}

- (int) sum: (int) to 
{
	if(to == -1)
		to = [cellWidths count];
	int sum = 0;
	for(int i = 0 ; i < to; i++ )
	{
		sum += [[cellWidths objectAtIndex:i] intValue];
	}
	return sum;
}

- (void)tabBar:(UITabBar *)tabBar didSelectItem:(UITabBarItem *)item
{
	if(item.tag == 1)
	{
		editView* screen = [[editView alloc] initWithNibName:nil bundle:nil];
		[screen setValues: titelStrings : nil];
		[self presentModalViewController:screen animated: YES];
		[screen release];	
	}
	else if(item.tag == 2)
	{	
		[anzeigeOptionen show];
	}
	else if(item.tag == 3)
	{
		if(selected == -1)
		{
			UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Bearbeitungs Optionen"
															  message:@"Bitte erst eine Zeile auswählen!."
															 delegate:nil
													cancelButtonTitle:@"Okay"
													otherButtonTitles:nil];
			[message show];
		}
		else {
			UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Bearbeitungs Optionen"
														  message:@"Bitte auswählen."
														 delegate:self
												cancelButtonTitle:@"Abbrechen"
												otherButtonTitles:nil];
			[message addButtonWithTitle:@"Bearbeiten"];
			[message addButtonWithTitle:@"Löschen"];
			[message show];
		}
	}
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
	if(alertView == anzeigeOptionen)
	{
		switch (buttonIndex) {
			case 0:
				//NSLog(@"Abbrechen");
				break;
			case 1:
				NSLog(@"");
				tabellenAuswahl* screen2 = [[tabellenAuswahl alloc] initWithNibName:nil bundle:nil];
				[screen2 setValues: [connect getTabels] ];
				[self presentModalViewController:screen2 animated: YES];
				[screen2 release];	
				break;
			case 2:
				NSLog(@"");
				orderBy* screen = [[orderBy alloc] initWithNibName:nil bundle:nil];
				[screen setValues: titelStrings];
				[self presentModalViewController:screen animated: YES];
				[screen release];				
				break;
			default:
				break;
		}
	}
	else {
	  switch (buttonIndex) {
		case 0:
			//NSLog(@"Abbrechen");
			break;
		case 1:
			NSLog(@"");
			editView* screen = [[editView alloc] initWithNibName:nil bundle:nil];
			[screen setValues: titelStrings : [contentStrings objectAtIndex: selected] ];
			[self presentModalViewController:screen animated: YES];
			[screen release];	
			break;
		case 2:
			if( ![connect deleteFrom:titelStrings : [contentStrings objectAtIndex:selected]])
			{
				UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Fehler"
																  message:@"Befehl konnte nicht ausgeführt werden. Probieren Sie's nochmal."
																 delegate:nil
														cancelButtonTitle:@"Okay"
														otherButtonTitles:nil];
				[message show];
			}
			else {
				[self refresh];
			}

			break;
		default:
			break;
	  }
	}
}

//macht leere zeilen unscichtbar
- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[[UIView alloc] init] autorelease];
	
    return view;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
	return [contentStrings count];
}

- (void) tableView: (UITableView *) tableView didSelectRowAtIndexPath: (NSIndexPath *) indexPath 
{ 
	selected = indexPath.row;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *simpleTableIdentifier = @"SimpleTableItem";
	
    tableCell *cell = (tableCell *) [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
	
	
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
		
		cell.selectionStyle = UITableViewCellSelectionStyleGray;
		
    }
	[self refreshCell:cell : indexPath];
    return cell;
}

- (void) didRotateFromInterfaceOrientation: (UIInterfaceOrientation) fromInterfaceOrientation
{
	[tabelle reloadRowsAtIndexPaths:[tabelle indexPathsForVisibleRows] withRowAnimation:UITableViewRowAnimationNone];
}

// only landscape
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    //return ((interfaceOrientation == UIInterfaceOrientationLandscapeLeft) || (interfaceOrientation == UIInterfaceOrientationLandscapeRight) );
	return YES;
}

-(void) didReceiveMemoryWarning
{
	[super didReceiveMemoryWarning];
	NSLog(@"Speicher leer");
}

-(void) dealloc
{
	[connect closeDb];
	[super dealloc];
}
@end
