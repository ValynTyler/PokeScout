use rust_xlsxwriter::*;

fn main() {
    write_xlsl().expect("ERROR");
}

fn write_xlsl() -> Result<(), XlsxError> {
    let mut workbook = Workbook::new();
    let worksheet = workbook.add_worksheet();

    worksheet.write(0, 0, "Index")?;
    worksheet.write(0, 1, "ID")?;
    worksheet.write(0, 2, "Pokemon")?;

    for i in 1..1026 {
        worksheet.write(i, 0, i)?;
        worksheet.write(i, 1, "ID")?;
        worksheet.write(i, 2, "Pokemon")?;
    }

    workbook.save("demo.xlsx")?;
    Ok(())
}
