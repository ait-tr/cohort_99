import { forwardRef, FC } from 'react';
import { TextField, TextFieldProps } from '@mui/material';
import { FieldConfig, FieldInputProps } from 'formik';

type TTextAreaProps = TextFieldProps & {
	getFieldProps?: <Value = any>(props: string | FieldConfig<Value>) => FieldInputProps<Value>;
	fieldName?: string;
	onChange?: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> | undefined;
};

export const TextArea: FC<TTextAreaProps> = forwardRef(function Input(
	{ getFieldProps, fieldName, ...props },
	ref
) {
	return (
		<>
			{getFieldProps && fieldName && (
				<TextField
					{...getFieldProps(`${fieldName}`)}
					ref={ref}
					variant="filled"
					multiline
					{...props}
				/>
			)}

			{!getFieldProps && <TextField ref={ref} variant="filled" multiline {...props} />}
		</>
	);
});
