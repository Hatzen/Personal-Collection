#import "app1ViewController.h"

@implementation app1ViewController

- (void)viewDidLoad
{
    [textfield setDelegate:self];
    [textfield setReturnKeyType:UIReturnKeyDone];
    [textfield addTarget:self action:@selector(textFieldFinished:) forControlEvents:UIControlEventEditingDidEndOnExit];
    [super viewDidLoad];
}

- (IBAction)anzeigen:(id)sender {
    [textanzeige setText: [textfield text]];
}

-(BOOL) textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}
@end
