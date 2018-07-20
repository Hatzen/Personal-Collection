#import "dbConnect.h"

@implementation dbConnect

-(void) openDb {
    // Den Pfad zur Documents-Directory in path speichern
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    dbPath = [documentsDirectory stringByAppendingPathComponent:@"database.sqlite3"];//shoppinglist.sqlite3
	
    NSFileManager *fileManager = [NSFileManager defaultManager];
    // Die Datenbank aus dem Bundle in die Documents-Directory kopieren
    NSString *pathInMainBundle = [[NSBundle mainBundle] pathForResource:@"database" ofType:@"sqlite3"];//:@"shoppinglist" ofType:@"sqlite3"
    if (![fileManager fileExistsAtPath:dbPath]) {
        NSLog(@"Datenabnk noch nicht vorhanden");
        [fileManager copyItemAtPath:pathInMainBundle toPath:dbPath error:nil];
    }
	
    // Die Datenbank öffnen
    int result = sqlite3_open([dbPath UTF8String], &database);
    if (result != SQLITE_OK) {
        sqlite3_close(database);
        NSLog(@"Fehler beim Öffnen der Datenbank");
        return;
    }
	order = [[NSString alloc] initWithString:@""];
	table  = [[NSString alloc] initWithString:@"CustomerCustomerDemo"];

}



-(void) closeDb {
    sqlite3_close(database);
}


- (void) orderBy: (NSString*) order2
{
	order =  order2;
}

- (void) setTable: (NSString*) table2
{
	table =  table2;
}

-(NSMutableArray *) getTabels {
    NSMutableArray *items = [[NSMutableArray alloc] init];
	NSString* query = [[[NSString alloc] initWithFormat:@"SELECT * FROM sqlite_master where type='table'"] autorelease];
	
    if (sqlite3_prepare_v2(database, [query UTF8String] , -1, &statement, nil) == SQLITE_OK) {
		int numb = sqlite3_column_count(statement);
		
		NSMutableArray *elemente = [[NSMutableArray alloc] init];
		
        while (sqlite3_step(statement) == SQLITE_ROW) {
			elemente = [[NSMutableArray alloc] init];
			for(int i = 0; i < numb ; i++)
            {
				NSString* tmp;
				if ( (char *)sqlite3_column_text(statement, i) == nil ) {
					tmp = [[NSString alloc] initWithString:@""];
				}
				else {
					tmp =  [NSString stringWithUTF8String:(char *)sqlite3_column_text(statement, i)];
				}
				if(tmp == nil )
					tmp = [[NSString alloc] initWithString:@""];
				[elemente addObject: tmp];
            }
			[items addObject:elemente];
        }
		
        sqlite3_finalize(statement);
    }
	if([items count] == 0)
	{
		NSMutableArray *elemente2 = [[NSMutableArray alloc] init];
		[elemente2 addObject:@"Fehler: Keine Daten"];
		[items addObject:elemente2];
	}
    return items;
}

-(NSMutableArray *) getItems {
    NSMutableArray *items = [[NSMutableArray alloc] init];
	NSString* query = [[[NSString alloc] initWithFormat:@"SELECT * FROM %@ %@",table , order] autorelease];
	//NSString* query = [[[NSString alloc] initWithFormat:@"SELECT * FROM sqlite_master where type='table'"] autorelease];
	
	NSLog(@"%@", query);
    if (sqlite3_prepare_v2(database, [query UTF8String] , -1, &statement, nil) == SQLITE_OK) {
		int numb = sqlite3_column_count(statement);
		
			NSMutableArray *elemente = [[NSMutableArray alloc] init];
			for(int i = 0; i < numb ; i++)
            {
				NSString* tmp;
				if ((char *)sqlite3_column_name(statement, i) == nil ) {
					tmp = [[NSString alloc] initWithString:@""];
				}
				else {
					tmp = [NSString stringWithUTF8String:(char *)sqlite3_column_name(statement, i)];
				}
				[elemente addObject: tmp];
            }
			[items addObject:elemente];
        
		
        while (sqlite3_step(statement) == SQLITE_ROW) {
			 elemente = [[NSMutableArray alloc] init];
			for(int i = 0; i < numb ; i++)
            {
				NSString* tmp;
				if ( (char *)sqlite3_column_text(statement, i) == nil ) {
					tmp = [[NSString alloc] initWithString:@""];
				}
				else {
					tmp =  [NSString stringWithUTF8String:(char *)sqlite3_column_text(statement, i)];
				}
				if(tmp == nil )
					tmp = [[NSString alloc] initWithString:@""];
				[elemente addObject: tmp];
            }
			[items addObject:elemente];
        }
		
        sqlite3_finalize(statement);
    }
	if([items count] == 0)
	{
		NSMutableArray *elemente = [[NSMutableArray alloc] init];
		[elemente addObject:@"Fehler: Tabelle nicht Gefunden"];
		NSMutableArray *elemente2 = [[NSMutableArray alloc] init];
		[elemente2 addObject:@"Fehler: Keine Daten"];
		[items addObject:elemente];
		[items addObject:elemente2];
	}
	else if ( [items count] == 1 )
	{
		NSMutableArray *elemente = [[NSMutableArray alloc] init];
		[elemente addObject:@"Fehler: Keine Daten"];
		for (int i = 0; i < [[items objectAtIndex:0] count ]-1 ; i++) {
			[elemente addObject:@" --- "];
		}
		[items addObject:elemente];
	}
    return items;
}

- (BOOL) deleteFrom:  (NSMutableArray*) titel : (NSMutableArray*) value
{
	NSString* query = [[NSString alloc] initWithFormat:@"DELETE FROM %@ WHERE",table];
	for(int i = 0 ; i < [titel count] ; i++)
	{
		if([[value objectAtIndex:i] intValue] == 0)
			query = [[NSString alloc] initWithFormat:@"%@ %@ LIKE '%@' ",query ,[titel objectAtIndex:i], [value objectAtIndex:i]];
		else 
			query = [[NSString alloc] initWithFormat:@"%@ %@ = %i ",query ,[titel objectAtIndex:i], [[value objectAtIndex:i] intValue] ];
		
		if([titel objectAtIndex:i] != [titel objectAtIndex:[titel count]-1])
			query = [[NSString alloc] initWithFormat:@"%@ AND ",query];
	}
	int succes = sqlite3_prepare_v2(database, [query UTF8String] , -1, &statement, nil);
	if(succes == SQLITE_OK)
	{
		if(SQLITE_DONE != sqlite3_step(statement))
		{
			NSLog( @"Error while deleting data: '%s'", sqlite3_errmsg(database));
			return NO;
		}
		else {
			return YES;
		}
	}
	else {
		NSLog( @"Error while deleting data: '%s'", sqlite3_errmsg(database));
		NSLog(@"nicht erfolgreich:  %i", succes);
		return NO;
	}
	sqlite3_finalize(statement);
}

- (BOOL) update: (NSMutableArray*) titel : (NSMutableArray*) value
{
	NSString* query = [[NSString alloc] initWithFormat:@"UPDATE %@ SET ", table];
	for(int i = 1 ; i < [titel count] ; i++)
	{
		if([[value objectAtIndex:i] intValue] == 0)
			query = [[NSString alloc] initWithFormat:@"%@ %@ = '%@' ",query ,[titel objectAtIndex:i], [value objectAtIndex:i]];
		else 
			query = [[NSString alloc] initWithFormat:@"%@ %@ = %i ",query ,[titel objectAtIndex:i], [[value objectAtIndex:i] intValue] ];
		
		if([titel objectAtIndex:i] != [titel objectAtIndex:[titel count]-1])
			query = [[NSString alloc] initWithFormat:@"%@ , ",query];
	}
	if([[value objectAtIndex:0] intValue] == 0)
		query = [[NSString alloc] initWithFormat:@"%@ WHERE %@ = '%@' ",query ,[titel objectAtIndex:0], [value objectAtIndex:0]];
	else 
		query = [[NSString alloc] initWithFormat:@"%@ WHERE %@ = %i ",query ,[titel objectAtIndex:0], [[value objectAtIndex:0] intValue] ];
	
	NSLog(@"das ist der query: %@", query);
	
	int succes = sqlite3_prepare_v2(database, [query UTF8String] , -1, &statement, nil);
	if(succes == SQLITE_OK)
	{
		if(SQLITE_DONE != sqlite3_step(statement))
		{
			NSLog( @"Error while inserting data: '%s'", sqlite3_errmsg(database));
			return NO;
		}
		else {
			return YES;
		}
	}
	else {
		NSLog(@"nicht erfolgreich:  %i", succes);
		return NO;
	}
	sqlite3_finalize(statement);
}

- (BOOL) insertInto: (NSMutableArray*) titel : (NSMutableArray*) value
{
	NSString* query = [[NSString alloc] initWithFormat: @"INSERT INTO %@ (", table];
	for(NSString* string in titel)
	{
		if(string != [titel objectAtIndex:0])
			query = [[NSString alloc] initWithFormat:@"%@ , %@ ",query, string];
		else 
			query = [[NSString alloc] initWithFormat:@"%@ %@ ",query , string];
	}
	if([[value objectAtIndex:0] intValue] == 0)
		query = [[NSString alloc] initWithFormat:@"%@ ) VALUES ( '%@' ",query ,[value objectAtIndex:0] ];
	else 
		query = [[NSString alloc] initWithFormat:@"%@ ) VALUES ( %i ",query ,[[value objectAtIndex:0] intValue] ];
	for(NSString* string in value)
	{
		if(string != [value objectAtIndex:0])
		{
			if([string intValue] == 0)
				query = [[NSString alloc] initWithFormat:@"%@ , '%@' ",query ,string];
			else 
				query = [[NSString alloc] initWithFormat:@"%@ , %i ",query ,[string intValue] ];
		}
	}
	query = [[NSString alloc] initWithFormat:@"%@ ) ",query];
	
	int succes = sqlite3_prepare_v2(database, [query UTF8String] , -1, &statement, nil);
	if(succes == SQLITE_OK)
	{
		if(SQLITE_DONE != sqlite3_step(statement))
		{
			NSLog( @"Error while inserting data: '%s'", sqlite3_errmsg(database));
			return NO;
		}
		else {
			return YES;
		}
	}
	else {
		NSLog(@"nicht erfolgreich:  %i", succes);
		return NO;
	}
	sqlite3_finalize(statement);
}

@end
