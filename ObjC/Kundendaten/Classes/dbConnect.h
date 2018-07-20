#import <Foundation/Foundation.h>
#import "sqlite3.h"


@interface dbConnect : NSObject {
	sqlite3 *database;
    NSString *dbPath;
	NSString *table;
    sqlite3_stmt *statement;
	NSString* order;
}

-(void) openDb;
-(void) closeDb;
-(NSMutableArray *) getItems;
-(NSMutableArray *) getTabels;


- (BOOL) insertInto: (NSMutableArray*) titel : (NSMutableArray*) value;
- (BOOL) deleteFrom:  (NSMutableArray*) titel : (NSMutableArray*) value;
- (BOOL) update: (NSMutableArray*) titel : (NSMutableArray*) value;

- (void) orderBy: (NSString*) order2;
- (void) setTable: (NSString*) table2;

@end
