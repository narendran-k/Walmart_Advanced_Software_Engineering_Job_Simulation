import pandas as pd
import sqlite3

# Define paths
DB_PATH = 'path_to_your_database.db'  # Update with the path to your SQLite database
SPREADSHEET_0_PATH = 'path_to_spreadsheet_0.xlsx'  # Update with the path to Spreadsheet 0
SPREADSHEET_1_PATH = 'path_to_spreadsheet_1.xlsx'  # Update with the path to Spreadsheet 1
SPREADSHEET_2_PATH = 'path_to_spreadsheet_2.xlsx'  # Update with the path to Spreadsheet 2

# Step 1: Connect to the SQLite database
conn = sqlite3.connect(DB_PATH)

# Step 2: Load the spreadsheets into DataFrames
spreadsheet_0 = pd.read_excel(SPREADSHEET_0_PATH)
spreadsheet_1 = pd.read_excel(SPREADSHEET_1_PATH)
spreadsheet_2 = pd.read_excel(SPREADSHEET_2_PATH)

# Step 3: Insert data from Spreadsheet 0 into the database
spreadsheet_0.to_sql('Products', conn, if_exists='append', index=False)  # Replace 'Products' with your actual table name

# Step 4: Process Spreadsheet 1 and Spreadsheet 2
# Merge the two spreadsheets based on the shipping identifier
merged_data = pd.merge(spreadsheet_1, spreadsheet_2, on='ShippingIdentifier')

# Step 5: Prepare data for insertion into the shipment table
for index, row in merged_data.iterrows():
    product_name = row['ProductName']  # Replace with the actual column name
    quantity = row['Quantity']          # Replace with the actual column name
    shipment_origin = row['Origin']     # Replace with the actual column name
    shipment_destination = row['Destination']  # Replace with the actual column name
    
    # Insert data into the shipments table
    conn.execute('''
        INSERT INTO Shipments (ProductName, Quantity, Origin, Destination) 
        VALUES (?, ?, ?, ?)''', 
        (product_name, quantity, shipment_origin, shipment_destination))

# Step 6: Commit the changes and close the connection
conn.commit()
conn.close()

print("Data has been successfully inserted into the database.")
