#import "tabellenAuswahl.h"
#import "KundendatenViewController.h"

@implementation tabellenAuswahl

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void) setValues: (NSMutableArray*) elemente2
{
	elemente = elemente2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
	return [elemente count];
}

- (void) tableView: (UITableView *) tableView didSelectRowAtIndexPath: (NSIndexPath *) indexPath 
{ 
	[[[KundendatenViewController class] connect] setTable: [[elemente objectAtIndex:indexPath.row] objectAtIndex:1]];
	UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Auswahl getroffen"
													 message:[[NSString alloc] initWithFormat:@"Tabelle \"%@\" erfolgreich Ausgewählt! ", [[elemente objectAtIndex:indexPath.row] objectAtIndex:1]]
													delegate:nil
										   cancelButtonTitle:@"Okay"
										   otherButtonTitles:nil];
	[message show];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *simpleTableIdentifier = @"SimpleTableItem";
    UITableViewCell *cell = (UITableViewCell *) [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
	
	
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
	}
	cell.text = [[elemente objectAtIndex:indexPath.row] objectAtIndex:1];
	
    return cell;
}


- (IBAction)clickBack:(id)sender
{
	[self dismissModalViewControllerAnimated:YES];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    return YES;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)viewDidUnload {
    [super viewDidUnload];
}


- (void)dealloc {
    [super dealloc];
}


@end
